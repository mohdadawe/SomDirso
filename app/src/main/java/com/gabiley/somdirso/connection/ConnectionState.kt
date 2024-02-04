package com.gabiley.somdirso.connection

sealed class ConnectionState {
   object Available : ConnectionState()
   object Unavailable : ConnectionState()
}
