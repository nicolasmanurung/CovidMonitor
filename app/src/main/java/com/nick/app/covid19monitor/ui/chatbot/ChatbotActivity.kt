package com.nick.app.covid19monitor.ui.chatbot

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibm.cloud.sdk.core.http.Response
import com.ibm.cloud.sdk.core.security.IamAuthenticator
import com.ibm.watson.assistant.v2.Assistant
import com.ibm.watson.assistant.v2.model.CreateSessionOptions
import com.ibm.watson.assistant.v2.model.MessageInput
import com.ibm.watson.assistant.v2.model.MessageOptions
import com.ibm.watson.assistant.v2.model.SessionResponse
import com.nick.app.covid19monitor.R
import com.nick.app.covid19monitor.data.source.remote.response.Message
import kotlinx.android.synthetic.main.content_chat_room.*
import java.util.*

class ChatbotActivity : AppCompatActivity() {
    private var messageArrayList: ArrayList<Message>? = null
    private var mAdapter: ChatAdapter? = null
    private var initialRequest = false
    private var watsonAssistantSession: Response<SessionResponse>? = null
    private lateinit var watsonAssistant: Assistant
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)

        context = applicationContext

        messageArrayList = ArrayList<Message>()
        mAdapter = ChatAdapter(messageArrayList!!)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        recycler_view.layoutManager = layoutManager
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.adapter = mAdapter
        message.setText("")
        initialRequest = true

        val actionBar = supportActionBar
        actionBar?.title = "DelBot"
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#8E9DC6")))



        btn_send.setOnClickListener {
            if (checkInternetConnection()) {
                sendMessage()
            }
        }

        createServices()
        sendMessage()

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    //Function
    private fun sendMessage() {
        var inputMessage: String = message.text.toString().trim()

        if (!initialRequest) {
            var inputmessage = Message()
            inputmessage.message = inputMessage
            inputmessage.id = "1"
            messageArrayList?.add(inputmessage)
        } else {
            var inputmessage = Message()
            inputmessage.message = inputMessage
            inputmessage.id = "100"
            initialRequest = false
            Toast.makeText(applicationContext, "Kirim Pesanmu", Toast.LENGTH_LONG).show()
        }

        message.setText("")
        mAdapter?.notifyDataSetChanged()

        var thread = Thread(Runnable {
            run {
                try {
                    if (watsonAssistantSession == null) {
                        val call = watsonAssistant.createSession(
                            CreateSessionOptions.Builder()
                                .assistantId(context.getString(R.string.assistant_id)).build()
                        )
                        watsonAssistantSession = call.execute()
                    }

                    val input =
                        MessageInput.Builder()
                            .text(inputMessage)
                            .build()

                    val options =
                        MessageOptions.Builder()
                            .assistantId(context.getString(R.string.assistant_id))
                            .input(input)
                            .sessionId(watsonAssistantSession?.result?.sessionId)
                            .build()

                    val response =
                        watsonAssistant.message(options).execute()

                    if (response != null && response.result.output != null && response.result.output.generic.isNotEmpty()) {
                        val responses =
                            response.result.output.generic

                        for (r in responses) {
                            var outMessage: Message
                            when (r.responseType()) {
                                "text" -> {
                                    outMessage = Message()
                                    outMessage.message = r.text()
                                    outMessage.id = "2"

                                    messageArrayList?.add(outMessage)
                                }
                                "option" -> {
                                    outMessage = Message()
                                    var title = r.title()
                                    var OptionsOutput = ""
                                    for (i in r.options().indices) {
                                        val option =
                                            r.options()[i]
                                        OptionsOutput = """
                                            $OptionsOutput${option.label}
                                            
                                            """.trimIndent()
                                    }
                                    outMessage.message = title + "\n" + OptionsOutput
                                    outMessage.id = "2"

                                    messageArrayList?.add(outMessage)
                                }

                                "image" -> {
                                    outMessage = Message(r)
                                    messageArrayList?.add(outMessage)
                                }
                                else -> {
                                    Log.e("ERROR", "Unhandled message type")
                                }
                            }
                        }

                        runOnUiThread {
                            run {
                                mAdapter?.notifyDataSetChanged()
                                if (mAdapter!!.itemCount > 1) {
                                    recycler_view.layoutManager?.smoothScrollToPosition(
                                        recycler_view,
                                        null,
                                        mAdapter!!.itemCount - 1
                                    )
                                }
                            }
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
        thread.start()
    }

    private fun createServices() {
        watsonAssistant = Assistant(
            "2020-04-01",
            IamAuthenticator(context.getString(R.string.assistant_apikey))
        )
        watsonAssistant.serviceUrl = context.getString(R.string.assistant_url)
    }

    private fun checkInternetConnection(): Boolean{

        // get Connectivity Manager object to check connection
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting


        // Check for network connections
        return if (isConnected) {
            true
        } else {
            Toast.makeText(this, "Tolong nyalain internetnya dong", Toast.LENGTH_LONG).show()
            false
        }
    }


}
