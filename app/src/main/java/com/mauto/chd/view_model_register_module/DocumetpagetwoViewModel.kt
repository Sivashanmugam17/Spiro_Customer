package com.mauto.chd.view_model_register_module

import android.app.Application
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mauto.chd.R
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.data.steptwodocumentdb.documenttwoRepository
import com.mauto.chd.data.steptwodocumentdb.documenttwodoRecord
import com.mauto.chd.ui.registeration.EightFoldsDatePickerDialog
//import com.developers.imagezipper.ImageZipper
//import fr.castorflex.android.smoothprogressbar.SmoothProgressBar
import org.json.JSONObject
import java.io.*
import java.util.*


class DocumetpagetwoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: documenttwoRepository = documenttwoRepository(application)
    private val allTodoList: LiveData<List<documenttwodoRecord>> = repository.getAllTodoList()
    private val datesave = MutableLiveData<String>()

    private val movenext = MutableLiveData<String>()


    fun saveTodo(todo: documenttwodoRecord) {
        repository.saveTodo(todo)
    }

    fun getAllTodoList(): LiveData<List<documenttwodoRecord>> {
        return allTodoList
    }


    fun movenextobserver(): MutableLiveData<String>
    {
        return movenext
    }





    fun datesaveobservervalue(): MutableLiveData<String>
    {
        return datesave
    }



    fun startsteptwoapi(mContext: Context)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.steptwoapi))
        mContext.startService(intent)
    }


    fun finalcalltohomepage(mContext: Context,type:String)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.finalcalltohomepage))
        intent.putExtra("type",type)
        mContext.startService(intent)
    }


    fun sendfinaldocument(mContext: Context,params: HashMap<String, String>)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.finaldocument))
        intent.putExtra("params", params)
        mContext.startService(intent)
    }

    fun returndob(mContext: Context)
    {
        // create an OnDateSetListener

        val calendar = Calendar.getInstance()
        val datePickerDialog = EightFoldsDatePickerDialog(
                mContext,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                    var newmonth= (month+1)
                    var days=dayOfMonth.toString()
                    if(dayOfMonth<10)
                        days="0"+dayOfMonth

                    var months=newmonth.toString()
                    if(newmonth<10)
                        months="0"+newmonth

                    val date = "$days/$months/$year"
                    datesave.value=date
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)
        )
        datePickerDialog.setTodayAsMinDate()
        datePickerDialog.show()
    }


//    fun onImagecompress(mContext: Context,picturePath: File,imagenames:String,loader: SmoothProgressBar,selectedImageUri: Uri)
//    {
//
//        try
//        {
//            try
//            {
//              val b = ImageZipper(mContext).compressToBitmap(picturePath)
//                saveimagetopath(b,mContext,loader,imagenames,selectedImageUri)
//            }
//            catch (e: Exception)
//            {
//                e.printStackTrace()
//            }
//        }
//        catch (e: IOException)
//        {
//            e.printStackTrace()
//        }
//    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        Log.d("dfsggfdg5774cdf",path)
        return Uri.parse(path)
    }


//    fun onImagecompresscamera(mContext: Context,b:Bitmap,imagenames:String,loader:SmoothProgressBar,selectedImageUri : Uri)
//    {
//        try
//        {
//            try
//            {
//                saveimagetopath(b,mContext,loader,imagenames,selectedImageUri)
//            }
//            catch (e: Exception)
//            {
//                e.printStackTrace()
//            }
//        }
//        catch (e: IOException)
//        {
//            e.printStackTrace()
//        }
//    }



    fun savedriverprofiledata(mContext: Context,response:String)
    {
        val response_json_object = JSONObject(response)
        try
        {
            val status = response_json_object.getString("status")
            if (status.equals("1"))
            {
                var mSessionManager: SessionManager? = null
                mSessionManager = SessionManager(mContext)
                mSessionManager!!.setHasseesion(true)

                val driver_id = response_json_object.getString("driver_id")
                val driver_name = response_json_object.getString("driver_name")
                val email = response_json_object.getString("email")
                val driver_image = response_json_object.getString("driver_image")


//                mSessionManager!!.setDocPending("0")
                movenext.value = "1"
            }
        }
        catch (e: Exception)
        {
        }
    }

    fun updatedocument(mContext: Context,params: HashMap<String, String>,categroy:String)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.finaldocumentupload))
        intent.putExtra("params", params)
        intent.putExtra("categroy", categroy)
        mContext.startService(intent)
    }

    fun splitresponse(mContext: Context,response:String,hidedocumentofdriver:Int)
    {
        try
        {
            val response_json_object = JSONObject(response)
            Log.d("mresponedetailes",response)
            try
            {
                val status = response_json_object.getString("status")
                if (status.equals("1"))
                {
                    var resultsresponse = JSONObject(response_json_object.getString("response"))
                    var documents = resultsresponse.getJSONArray("documents")
                    for (i in 0 until documents.length())
                    {
                        if(hidedocumentofdriver == 1)
                        {
                            var id = documents.getJSONObject(i).getString("id")
                            var name = documents.getJSONObject(i).getString("name")
                            var category = documents.getJSONObject(i).getString("type")
                            var is_req = documents.getJSONObject(i).getString("is_req")
                            var is_exp = documents.getJSONObject(i).getString("is_exp")
                            var notes = mContext.getString(R.string.notess)+documents.getJSONObject(i).getString("notes")

                            var has_front = documents.getJSONObject(i).getString("has_front")
                            var has_back = documents.getJSONObject(i).getString("has_back")
                            var has_input = documents.getJSONObject(i).getString("has_input")



                            if(category.equals("Vehicle"))
                            {
                                if(has_front.equals("1") && has_back.equals("1"))
                                {
                                    saveStepTwo(id,name,category,is_req,is_exp,notes,has_input,"3","0")
                                }
                                else if(has_front.equals("1") && has_back.equals("0"))
                                {
                                    saveStepTwo(id,name,category,is_req,is_exp,notes,has_input,"1","0")
                                }
                                else if(has_front.equals("0") && has_back.equals("1"))
                                {
                                    saveStepTwo(id,name,category,is_req,is_exp,notes,has_input,"2","0")
                                }
                                else
                                {
                                    saveStepTwo(id,name,category,is_req,is_exp,notes,has_input,"0","0")
                                }
                            }


                        }
                        else
                        {
                            var id = documents.getJSONObject(i).getString("id")
                            var name = documents.getJSONObject(i).getString("name")
                            var category = documents.getJSONObject(i).getString("type")
                            var is_req = documents.getJSONObject(i).getString("is_req")
                            var is_exp = documents.getJSONObject(i).getString("is_exp")
                            var notes = mContext.getString(R.string.notess)+documents.getJSONObject(i).getString("notes")

                            var has_front = documents.getJSONObject(i).getString("has_front")
                            var has_back = documents.getJSONObject(i).getString("has_back")
                            var has_input = documents.getJSONObject(i).getString("has_input")


                            if(has_front.equals("1") && has_back.equals("1"))
                            {
                                saveStepTwo(id,name,category,is_req,is_exp,notes,has_input,"3","0")
                            }
                            else if(has_front.equals("1") && has_back.equals("0"))
                            {
                                saveStepTwo(id,name,category,is_req,is_exp,notes,has_input,"1","0")
                            }
                            else if(has_front.equals("0") && has_back.equals("1"))
                            {
                                saveStepTwo(id,name,category,is_req,is_exp,notes,has_input,"2","0")
                            }
                            else
                            {
                                saveStepTwo(id,name,category,is_req,is_exp,notes,has_input,"0","0")
                            }
                        }

                    }
                }
            }
            catch (e: Exception)
            {
            }
        }
        catch (e: Exception)
        {

        }
    }



    fun saveStepTwo(id:String,name:String,category:String,is_req:String,is_exp:String,notes:String,has_input:String,fronorback:String,submittedfully:String)
    {
        var todoRecord: documenttwodoRecord? = null
        val comid = if (todoRecord != null) todoRecord?.id else null
        val todo = documenttwodoRecord(id = comid, coloumnid = id, coloumnname = name,category = category,is_req = is_req,is_exp = is_exp,has_input=has_input,notes = notes,account_number="",expirydate = "",imagestore = "",imagestore1 = "",uploadstatus = "",frontandback=fronorback,submitedfully = submittedfully,document_name = "",uuid = "")
        saveTodo(todo)
    }

    fun updateTodo(todo: documenttwodoRecord){
        repository.updateTodo(todo)
    }





//    fun saveimagetopath(b: Bitmap, mContext:Context, loader:SmoothProgressBar,imagenames:String,selectedImageUri : Uri)
//    {
////        var path:String = Environment.getExternalStorageDirectory().toString();
////        var  fOut: OutputStream
////        var file:File =  File(path, imagenames+".jpg")
////        Log.d("dsfgsg", file.toString())
////        Log.d("dsfgdfdsg", path.toString())
////
////        fOut = FileOutputStream(file)
////        b.compress(Bitmap.CompressFormat.JPEG, 85, fOut)
////        fOut.flush()
////        fOut.close()
//        val filePath = arrayOf(MediaStore.Images.Media.DATA)
//        val c: Cursor? = mContext.getContentResolver().query(selectedImageUri, filePath, null, null, null)
//        c!!.moveToFirst()
//        val columnIndex: Int = c.getColumnIndex(filePath[0])
//        val picturePath: String = c.getString(columnIndex)
//        c.close()
//        val curFile = File(picturePath)
//
//
//        uploadimagetoserver(mContext,curFile,loader)
//    }

//    fun uploadimagetoserver(mContext:Context,file:File,loader:SmoothProgressBar)
//    {
//        loader.visibility= View.VISIBLE
//        val serviceClass = uploadimagetoserverusingamazonbucket::class.java
//        val intent = Intent(mContext, serviceClass)
//        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), file.toString())
//        intent.putExtra("state", "3")
//        mContext.startService(intent)
//    }


}
