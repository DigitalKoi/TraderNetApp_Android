package com.koidev.tradernet.service

import com.koidev.tradernet.model.Subscribe
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.flow.Flow

interface CoinbaseService {

    @Receive
    fun observeWebSocket(): Flow<WebSocket.Event>

    @Send
    fun sendSubscribe(subscribe: String)

    @Receive
    fun observeTicker(): Flow<Any>
}
