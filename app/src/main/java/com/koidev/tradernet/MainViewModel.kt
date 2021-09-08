package com.koidev.tradernet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koidev.tradernet.service.CoinbaseService
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MainViewModel @Inject constructor(
        service: CoinbaseService
        ) : ViewModel() {

            init {
                service.observeWebSocket()
            .flowOn(Dispatchers.IO)
            .onEach { event ->
                when (event) {
                    is WebSocket.Event.OnMessageReceived -> {
                        val i = event
                    }
                    is WebSocket.Event.OnConnectionOpened<*> -> {
                        service.sendSubscribe(
                            REQUEST
                        )
                    }
                }
            }.launchIn(viewModelScope)

        service.observeTicker()
            .flowOn(Dispatchers.IO)
            .onEach { data ->
                val i = data
            }
    }


    private companion object {
        const val REQUEST = "[" +
                "\"realtimeQuotes\", " +
                "[" +
                "\"RSTI\"," +
                "\"GAZP\",\"MRKZ\",\"RUAL\",\"HYDR\",\"MRKS\",\"SBER\",\"FEES\",\"TGKA\"," +
                "\"VTBR\",\"ANH.US\",\"VICL.US\",\"BURG.US\",\"NBL.US\",\"YETI.US\",\"WSFS.US\"," +
                "\"NIO.US\",\"DXC.US\",\"MIC.US\",\"HSBC.US\",\"EXPN.EU\",\"GSK.EU\",\"SHP.EU\"," +
                "\"MAN.EU\",\"DB0.EU\",\"MUV2.EU\",\"TATE.EU\",\"KGF.EU\",\"MGGT.EU\",\"SGGD.EU\"" +
                "]" +
                "]"
    }
}