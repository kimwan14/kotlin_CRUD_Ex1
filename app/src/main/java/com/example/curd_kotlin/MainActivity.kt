package com.example.curd_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.curd_kotlin.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var db: AppDatabase
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Database 객체 생성
        db = AppDatabase.getInstance(this)

        // 회원가입 및 로그인 버튼 클릭
        binding.btnLogin.setOnClickListener {
            email = binding.etEmail.text.toString()
            password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    if (db.getLoginDAO().getEmailList().contains(email)) {
                        if (db.getLoginDAO().getPasswordByEmail(email) == password) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(applicationContext, "이미 가입된 계정입니다. 로그인을 진행합니다.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(applicationContext, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        db.getLoginDAO().insertUser(UserEntity(email = email, password = password))
                        withContext(Dispatchers.Main) {
                            Toast.makeText(applicationContext, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        // 회원탈퇴 버튼 클릭
        binding.btnDelete.setOnClickListener {
            email = binding.etEmail.text.toString()
            password = binding.etPassword.text.toString()

            CoroutineScope(Dispatchers.Default).launch {
                if (db.getLoginDAO().getEmailList().contains(email)) {
                    db.getLoginDAO().deleteUser(email, password)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(applicationContext, "회원탈퇴가 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(applicationContext, "등록된 계정이 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}