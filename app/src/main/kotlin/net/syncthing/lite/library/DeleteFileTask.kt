package net.syncthing.lite.library

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.syncthing.java.client.SyncthingClient
import net.syncthing.java.core.utils.PathUtils
import net.syncthing.lite.utils.Util
import org.apache.commons.io.IOUtils

class DeleteFileTask(private val context: Context,
                     private val syncthingClient: SyncthingClient,
                     private val syncthingFolder: String,
                     private val syncthingPath: String,
                     private val onComplete: () -> Unit,
                     private val onError: () -> Unit)
{
    companion object {
        private const val TAG = "DeleteFileTask"
        private val handler = Handler(Looper.getMainLooper())
    }

    init {
        Log.i(TAG, "Deleting file $syncthingFolder:$syncthingPath")

        GlobalScope.launch {
            try {
                val blockPusher = syncthingClient.getBlockPusher(folderId = syncthingFolder)
                blockPusher.pushDelete(folderId = syncthingFolder, targetPath = syncthingPath)
                handler.post { onComplete() }
            } catch (ex: Exception) {
                handler.post { onError() }
            }
        }
    }
}
