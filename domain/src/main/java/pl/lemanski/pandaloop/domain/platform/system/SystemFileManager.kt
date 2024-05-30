package pl.lemanski.pandaloop.domain.platform.system

import pl.lemanski.pandaloop.domain.model.file.File

interface SystemFileManager {
    fun createFile(path: String): File
    fun deleteFile(path: String)
    fun listFiles(path: String): List<File>
}