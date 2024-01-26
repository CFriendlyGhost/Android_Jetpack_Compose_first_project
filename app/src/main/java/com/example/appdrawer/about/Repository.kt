package com.example.appdrawer.about

class Repository private constructor() {

    private var dataObserver: DataObserver? = null
    private val dataList: MutableList<String> = mutableListOf()
    private var photoId: Int? = null

    init {
            dataList.add(0, "This is my App.")
            dataList.add(1, "Welcome!")
        }

    fun getDataList(): List<String> {
        return dataList
    }

    fun getImageId(): Int? {
        return photoId
    }

    fun addTextData(welcomeText: String, invitation: String) {
        dataList.add(0, welcomeText)
        dataList.add(1, invitation)
        notifyDataChanged()
    }

    fun addPhotoData(photo: Int){
        photoId = photo
        notifyDataChanged()
    }

    fun clearData() {
        dataList.clear()
    }

    //TODO
    fun registerObserver(observer: DataObserver) {
        dataObserver = observer
    }

    private fun notifyDataChanged() {
        dataObserver?.onDataChanged()
    }

    companion object {
        private var instance: Repository? = null

        fun getInstance(): Repository {
            if (instance == null) {
                instance = Repository()
            }
            return instance as Repository
        }
    }
}