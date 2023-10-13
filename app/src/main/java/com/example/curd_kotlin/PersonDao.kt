package com.example.curd_kotlin

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LoginDAO {
    @Query("SELECT email FROM userInfo")
    fun getEmailList(): List<String>    // 등록된 회원인지 확인

    @Query("SELECT password FROM userinfo WHERE email = :email")    // 이메일에 따른 비밀번호 반환
    fun getPasswordByEmail(email: String): String

    @Insert
    fun insertUser(userInfo: UserEntity)    // 회원 등록

    @Query("DELETE FROM userinfo WHERE email = :email AND password = :password")
    fun deleteUser(email: String, password: String)    // 회원 삭제
}