package com.catnip.moviegate.data.network

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

enum class State{
    RUNNING,SUCCESS,FAILED,END
}
class PaginateResultState (val state: State,val message:String){
    companion object{
        val LOADED: PaginateResultState
        val LOADING: PaginateResultState
        val ERROR: PaginateResultState
        val EOF: PaginateResultState
        init {
            LOADED = PaginateResultState(State.SUCCESS,"Success Load Data")
            LOADING = PaginateResultState(State.RUNNING,"Running")
            ERROR = PaginateResultState(State.FAILED,"Getting data failed")
            EOF = PaginateResultState(State.END,"All Content Loaded")
        }
    }
}