package com.example.shows_tomislavlovrencic.Fragments

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.shows_tomislavlovrencic.Models.AddEpisodeViewModel
import com.example.shows_tomislavlovrencic.Models.MediaViewModel
import com.example.shows_tomislavlovrencic.R
import com.example.shows_tomislavlovrencic.classes.EpisodeApi
import kotlinx.android.synthetic.main.fragment_add_episode.*
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*


var myBitmap: Bitmap? = null
var imagePath : String? = null
var episodeLocal : EpisodeApi? = null

private val Gallery = 1
private val Camera = 2
const val VALUE_PICKER = 10
var numberPickerValue1 = 1
var numberPickerValue2 = 1
const val EPISODE = "episode"
const val KEY = "key"
const val REQUEST_CAMERA_PERMISSION = 100

var path :String = ""

var tokenString : String = ""


class AddEpisodeFragment : Fragment() {

    private lateinit var viewModel: AddEpisodeViewModel
    private lateinit var viewModel2: MediaViewModel




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_episode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProviders.of(this).get(AddEpisodeViewModel::class.java)

        tokenString = viewModel.getToken()!!

        btnBackAddEpisodes.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        textViewUploadPhoto.setOnClickListener {
            showPictureDialog()
        }


        buttonSave.setOnClickListener {
            if(editTextShowName.text.length < 50){

            }
            if(editTextShowName.text.toString() == "" || editTextShowDescription.text.toString().length < DESCRIPTION_LIMIT || imgPhotoUploadPhoto.visibility == View.GONE){
                Toast.makeText(view.context, "You have to fill up everything!", Toast.LENGTH_SHORT).show()
            }
            else
            {
                addMedia()
            }

        }

        textViewShowSeasonPicker.setOnClickListener {
            showNumberPicker()
        }

        textViewChangePhoto.setOnClickListener {
            showPictureDialog()
        }

        if (savedInstanceState != null) {
            if (myBitmap != null) {
                myBitmap = savedInstanceState.getParcelable(KEY)
                imgPhotoUploadPhoto.setImageBitmap(myBitmap)
                imgLogoUploadPhoto.visibility = View.GONE
                textViewUploadPhoto.visibility = View.GONE
                textViewChangePhoto.visibility = View.VISIBLE
                imgPhotoUploadPhoto.visibility = View.VISIBLE
            }
        }
    }


    private fun showNumberPicker() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.layout_numberpicker)
        var np1 = dialog.findViewById<NumberPicker>(R.id.numberPicker1)
        var np2 = dialog.findViewById<NumberPicker>(R.id.numberPicker2)
        var button = dialog.findViewById<TextView>(R.id.buttonNumberPickerSave)



        np1.maxValue = 20
        np1.minValue = 1
        np2.maxValue = 99
        np2.minValue = 1
        np1.wrapSelectorWheel = false;
        np2.wrapSelectorWheel = false;

        button.setOnClickListener {
            if (np1.value < VALUE_PICKER && np2.value < VALUE_PICKER) {
                numberPickerValue1 = np1.value
                numberPickerValue2 = np2.value
                textViewShowSeasonPicker.text = "S 0" + np1.value.toString() + ", " + "E 0" + np2.value.toString()
                dialog.dismiss()
            } else {
                if (np1.value > VALUE_PICKER && np2.value > VALUE_PICKER) {
                    numberPickerValue1 = np1.value
                    numberPickerValue2 = np2.value
                    textViewShowSeasonPicker.text = "S " + np1.value.toString() + ", " + "E " + np2.value.toString()
                    dialog.dismiss()
                } else {
                    if (np1.value > VALUE_PICKER && np2.value < VALUE_PICKER) {
                        numberPickerValue1 = np1.value
                        numberPickerValue2 = np2.value
                        textViewShowSeasonPicker.text =
                            "S " + np1.value.toString() + ", " + "E 0" + np2.value.toString()
                        dialog.dismiss()
                    } else {
                        numberPickerValue1 = np1.value
                        numberPickerValue2 = np2.value
                        textViewShowSeasonPicker.text =
                            "S 0" + np1.value.toString() + ", " + "E " + np2.value.toString()
                        dialog.dismiss()
                    }
                }
            }
        }
        dialog.show()
    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(requireContext())
        val pictureDialogItems = arrayOf("Gallery", "Camera")
        pictureDialog.setItems(
            pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> handleCameraPermission()
            }
        }
        pictureDialog.show()
    }

    fun choosePhotoFromGallary() {
        startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), Gallery)
    }

    fun takePhotoFromCamera() {
        startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), Camera)
    }

    private fun handleCameraPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            takePhotoFromCamera()
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                androidx.appcompat.app.AlertDialog.Builder(activity!!)
                    .setTitle("Hej treba nam dozvola")
                    .setNeutralButton("OK") { dialog, _ ->
                        dialog.dismiss()
                        requestPermissions(
                            arrayOf(Manifest.permission.CAMERA),
                            REQUEST_CAMERA_PERMISSION
                        )
                    }.create().show()

            } else {
               requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
            }

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED) {
                takePhotoFromCamera()

            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Gallery) {
            if (data != null) {
                var contentURI = data.data

                try {
                    var bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, contentURI)
                    myBitmap = bitmap
                    imgPhotoUploadPhoto!!.setImageBitmap(bitmap)
                    imgPhotoUploadPhoto.visibility = View.VISIBLE
                    textViewChangePhoto.visibility = View.VISIBLE
                    imgLogoUploadPhoto.visibility = View.GONE
                    textViewUploadPhoto.visibility = View.GONE


                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        } else if (requestCode == Camera) {
            var thumbnail = data!!.extras!!.get("data") as Bitmap
            myBitmap = thumbnail

            imgPhotoUploadPhoto!!.setImageBitmap(thumbnail)
            imgPhotoUploadPhoto.visibility = View.VISIBLE
            textViewChangePhoto.visibility = View.VISIBLE
            imgLogoUploadPhoto.visibility = View.GONE
            textViewUploadPhoto.visibility = View.GONE


        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY, myBitmap)
        outState.clear()

    }

    fun addEpisode(path : String) {

        var episodeRandomId = generateRandomId()

        val episode = EpisodeApi(
            showId = showIdResult!!,
            imageUrl = path,
            mediaId = path,
            title = editTextShowName.text.toString(),
            description = editTextShowDescription.text.toString(),
            season = numberPickerValue1.toString(),
            episodeNmb = numberPickerValue2.toString(),
            episodeId = episodeRandomId
        )


        viewModel.setData(tokenString!!,episode)

        viewModel.liveData.observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                if (it.isSuccessful) {
                    episodeLocal = it.episode
                    boolean = true
                    Toast.makeText(requireContext(), "Episode added", Toast.LENGTH_SHORT).show()
                    fragmentManager?.popBackStack()
                } else {
                    viewModel.resetLiveData()
                    val dialog = AlertDialog.Builder(requireContext())
                    dialog.setTitle(it.message)
                    dialog.setPositiveButton("TRY AGAIN") { dialog, which ->
                        dialog.dismiss()
                    }
                    dialog.show()
                }
            }
        })
    }

    fun addMedia(){


        val pls : Uri = bitmapToUri(myBitmap!!)

        val image  =  File(pls.encodedPath)
        val requestBody : RequestBody = RequestBody.create(MediaType.parse("image/jpg"),image)

        viewModel2 = ViewModelProviders.of(this).get(MediaViewModel::class.java)

        viewModel2.addMedia(tokenString!!,requestBody)


        viewModel2.liveData.observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                if (it.isSuccessful) {
                    imagePath = it.apiAddMedia?.id
                    addEpisode(imagePath!!)
                } else {
                    viewModel2.resetLiveData()
                    val dialog = AlertDialog.Builder(requireContext())
                    dialog.setTitle(it.message)
                    dialog.setPositiveButton("TRY AGAIN") { dialog, which ->
                        dialog.dismiss()
                    }
                    dialog.show()
                }
            }

        })
    }

    private fun bitmapToUri(bitmap:Bitmap): Uri {

        val wrapper = ContextWrapper(requireContext())

        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file,"${UUID.randomUUID()}.jpg")

        try{
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
            stream.flush()
            stream.close()
        }catch (e:IOException){
            e.printStackTrace()
        }
        return Uri.parse(file.absolutePath)
    }

    fun generateRandomId(): String {
        val chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var id = ""
        for (i in 0..20) {
            id += chars[Math.floor(Math.random() * chars.length).toInt()]
        }
        return id
    }




}
