package net.syncthing.lite.dialogs

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import net.syncthing.java.bep.BlockPusher
import net.syncthing.java.client.SyncthingClient
import net.syncthing.java.core.utils.PathUtils
import net.syncthing.lite.R
import net.syncthing.lite.library.DeleteFileTask
import net.syncthing.lite.utils.Util
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast

class DeleteFileDialog(private val context: Context,
                       private val syncthingClient: SyncthingClient,
                       private val syncthingFolder: String,
                       private val syncthingPath: String,
                       private val onDeleteCompleteListener: () -> Unit) {

    private lateinit var progressDialog: ProgressDialog
    private var deleteFileTask: DeleteFileTask? = null

    fun show() {
        showDialog()
        doAsync {
            deleteFileTask = DeleteFileTask(context, syncthingClient, syncthingFolder,
                    syncthingPath, this@DeleteFileDialog::onComplete, this@DeleteFileDialog::onError)
        }
    }

    private fun showDialog() {
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage(context.getString(R.string.dialog_deleting_file))
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true
        progressDialog.show()
    }

    private fun onComplete() {
        progressDialog.dismiss()
        this@DeleteFileDialog.context.toast(R.string.toast_delete_complete)
        onDeleteCompleteListener()
    }

    private fun onError() {
        progressDialog.dismiss()
        this@DeleteFileDialog.context.toast(R.string.toast_file_delete_failed)
    }
}
