package com.atom.personnel.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "setting")
@Entity
data class Setting(

    @Id
    @GeneratedValue(GenerationType.IDENTITY)
    var id : Long ?= null,



)
