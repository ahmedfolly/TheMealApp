package com.example.themealapp.utils

data class Resource<out T>(
    val status: Status,
    val data: T?=null,
    val message: String?=null
){
    companion object{
        fun <T> success(data:T):Resource<T>{
            return Resource(status = Status.SUCCESS,data,null)
        }
        fun <T> loading(data:T?):Resource<T>{
            return Resource(Status.LOADING,null,null)
        }
        fun <T> failed(data:T?=null,message:String):Resource<T>{
            return Resource(Status.FAIL,null,message)
        }

    }
}