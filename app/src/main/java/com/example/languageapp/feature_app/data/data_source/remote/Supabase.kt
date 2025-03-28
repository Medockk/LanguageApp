package com.example.languageapp.feature_app.data.data_source.remote

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object Supabase {

    val client = createSupabaseClient(
        "https://uftclonibwagnofwkbtp.supabase.co",
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InVmdGNsb25pYndhZ25vZndrYnRwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDI0NjMxNTUsImV4cCI6MjA1ODAzOTE1NX0.Svf2If0KUdeEaPu6Z_YXZZgVf13dsXK2A78v0jSqWb0"
    ){
        install(Auth)
        install(Postgrest)
        install(Storage)
    }
}