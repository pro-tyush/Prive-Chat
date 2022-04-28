package com.pratyush.privechat.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import com.pratyush.privechat.adapter.UserAdapter
import com.pratyush.privechat.databinding.ActivityUsersBinding
import com.pratyush.privechat.firebase.FirebaseService
import com.pratyush.privechat.model.User

class UsersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUsersBinding
    var userList = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseService.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)

//        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
//            FirebaseService.token = it.token
//        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if(it.isComplete){
               FirebaseService.token = it.result
            }
        }


        binding.userRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        binding.imgProfile.setOnClickListener {
            val intent = Intent(
                this@UsersActivity,
                ProfileActivity::class.java
            )
            startActivity(intent)
        }
        getUsersList()
    }

    fun getUsersList() {
        val firebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!

        var userid = firebase.uid
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/$userid")


        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Users")


        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                val currentUser = snapshot.getValue(User::class.java)
                if (currentUser!!.profileImage == ""){
                    Toast.makeText(applicationContext,"a",Toast.LENGTH_SHORT).show()
                    binding.imgProfile.setImageResource(com.pratyush.privechat.R.drawable.profile_image)
                }else{
                    Toast.makeText(applicationContext,"b",Toast.LENGTH_SHORT).show()
                    Glide.with(this@UsersActivity).load(currentUser.profileImage).into(binding.imgProfile)
                }

                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    val user = dataSnapShot.getValue(User::class.java)

                    if (!user!!.userId.equals(firebase.uid)) {

                        userList.add(user)
                    }
                }

                val userAdapter = UserAdapter(this@UsersActivity, userList)

                binding.userRecyclerView.adapter = userAdapter
            }

        })
    }
}