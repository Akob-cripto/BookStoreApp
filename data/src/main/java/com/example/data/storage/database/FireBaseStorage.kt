package com.example.data.storage.database


import android.content.Context
import android.net.Uri
import android.util.Base64
import android.util.Log
import com.example.data.storage.UserStorage
import com.example.data.storage.models.DataAuthRequest
import com.example.data.storage.models.DataAuthUser
import com.example.domain.models.NewBookParam
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import androidx.core.net.toUri
import java.io.FileNotFoundException
import java.io.InputStream

class FireBaseStorage(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val context: Context
) : UserStorage {

    override suspend fun signUpFirebase(user: DataAuthRequest): DataAuthUser {
        try {
            Log.e("MyLog", "firebase save before await")
            auth.createUserWithEmailAndPassword(
                user.email,
                user.password
            ).await()
            Log.e("MyLog", "firebase save after await")
        } catch (e: Exception) {
            Log.e("MyLog", "firebase save error: ${e.message}", e)
            throw e
        }
        return DataAuthUser(email = user.email, userId = auth.currentUser?.uid ?: "")
    }

    override suspend fun signInFirebase(user: DataAuthRequest): DataAuthUser {
        try {
            Log.e("MyLog", "firebase signIn before await")
            auth.signInWithEmailAndPassword(
                user.email,
                user.password
            ).await()
            Log.e("MyLog", "firebase signIn after await")
        } catch (e: Exception) {
            Log.e("MyLog", "firebase signIn error: ${e.message}", e)
            throw e
        }
        return DataAuthUser(email = user.email, userId = auth.currentUser?.uid ?: "")
    }

    override suspend fun isAdminFirebase(): Boolean {
        val uid = auth.currentUser?.uid ?: return false

        return try {
            val doc = firestore.collection("Users")
                .document(uid)
                .get()
                .await()

            doc.getBoolean("isAdmin") == true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun saveBookFirebase(param: NewBookParam): Boolean {
        val uri = param.imageUri.toUri()
        Log.d("MyLog", "in saveBookFirebase1 $uri")
        val stringUri = convertUriToBase64(context, uri)
        Log.d("MyLog", "in saveBookFirebase2 $stringUri")
        val db = firestore.collection("books")
        val id = db.document().id
        try{
            db.document(id)
                .set(
                    param.copy(
                        imageUri = stringUri ?: "",
                         id = id))
                .await()
            return true
        }catch (e: Exception){
            return false
        }
    }

    private fun convertUriToBase64(context: Context, imageUri: Uri): String? {
        return try {
            // Проверяем URI
            if (imageUri == Uri.EMPTY) {
                Log.e("MyLog", "URI is EMPTY")
                return null
            }

            val inputStream = context.contentResolver.openInputStream(imageUri)

            if (inputStream == null) {
                Log.e("MyLog", "openInputStream returned NULL for URI: $imageUri")
                return null
            }

            // Читаем байты
            val bytes = inputStream.readBytes()
            inputStream.close()

            Log.d("MyLog", "Read ${bytes.size} bytes")

            if (bytes.isEmpty()) {
                Log.e("MyLog", "Bytes array is EMPTY!")
                return null
            }

            val base64String = Base64.encodeToString(bytes, Base64.DEFAULT)

            base64String

        }  catch (e: FileNotFoundException) {
            Log.e("MyLog", "FileNotFoundException: File not found")
            null
        } catch (e: Exception) {
            Log.e("MyLog", "Unexpected error", e)
            null
        }
    }
}