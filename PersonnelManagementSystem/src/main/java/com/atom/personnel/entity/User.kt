package com.atom.personnel.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Table(name = "user")
@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = 0,

    @Column(name = "username", length = 20, nullable = false)
    var username : String = "",

    @Column(name = "password")
    var password : String = "",

    @Column(name = "phone", length = 11)
    var phone : String = "",

    @Column(name = "last_login_time")
    var lastLoginTime : LocalDateTime = LocalDateTime.now(),

    @Column(name = "auto_login")
    var autoLogin : String = "OFF",

    @Column(name = "ip_address", length = 15)
    var ipAddress : String = "",

    @Column(name = "province",length = 20)
    var province : String = "",

    @Column(name = "has_logined")
    var hasLogined : Int = 0
)

