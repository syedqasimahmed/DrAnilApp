package mmcom.ui.dranil.model

import mmcom.ui.dranil.models.User

/**
 * Created by Qasim Ahmed on 25/12/2018.
 */
/*
{"status":"success","message":"User found","data":
{"id":"2","name":"sdfasdf","email":"test@gmail.com","accessToken":"test123"}}
 */


data class LoginResponse(val status:String,val message:String , val data:User )