package com.example.appdrawer.settings

import android.content.Context
import com.example.appdrawer.R

class DataRepo (context : Context) {
    private var dataList: MutableList<DataEntity>? = null
    private var dataObserver: DataObserver? = null
    private var dataDao: DataDao
    private var db: AppDatabase

    init {
        db = AppDatabase.getDatabase(context)!!
        dataDao = db.dataDao()!!
//        dataDao.deleteAllData()
//        addSampleData()
    }

    fun getDataList(): List<DataEntity> {
        return dataDao.getAllData()
    }

    private fun addSampleData() {
        val imageList = arrayOf(
            R.drawable.motor,
            R.drawable.images,
            R.drawable.motor,
            R.drawable.images,
            R.drawable.motor,
            R.drawable.images,
            R.drawable.motor,
            R.drawable.images,
            R.drawable.images,
            R.drawable.images,
            R.drawable.yacht,
            R.drawable.yacht,
            R.drawable.yacht
        )
        val ratingList = arrayOf(1.0f, 2.5f, 3.5f, 5.0f, 1.0f, 2.0f, 4.5f, 2.5f, 3.5f, 5.0f, 5.0f, 3.5f, 4.5f)
        val titleList = arrayOf(
            "Ducati",
            "Ferrari",
            "Aprilia",
            "BMW",
            "Suzuki",
            "Audi",
            "Kawasaki",
            "Opel",
            "Chevrolet",
            "Ford",
            "Ferretti",
            "Riva",
            "Zeelander",
        )
        val descList = arrayOf(
            "Ducati motorcycle brand.",
            "Ferrari luxury sports car.",
            "Aprilia motorcycle brand.",
            "BMW luxury automobile manufacturer.",
            "Suzuki motorcycle and automobile brand.",
            "Audi premium automobile brand.",
            "Kawasaki motorcycle brand.",
            "Opel automobile manufacturer.",
            "Chevrolet automobile brand.",
            "Ford automobile manufacturer.",
            "Ferretti is an Italian manufacturer of luxury motor yachts",
            "Riva is recognized as an Italian brand of motor yachts with elegant design",
            "Zeelander is a company specializing in the production of stylish and exclusive sport yachts."

        )
        val phoneNumList = arrayOf(
            "123-456-789",
            "123-444-789",
            "222-456-789",
            "123-555-789",
            "123-233-789",
            "123-456-789",
            "123-456-789",
            "123-456-789",
            "123-456-789",
            "123-456-789",
            "123-456-789",
            "123-456-789",
            "123-456-789",
        )

        val categories = arrayOf(
            "Motorcycle",
            "Car",
            "Motorcycle",
            "Car",
            "Motorcycle",
            "Car",
            "Motorcycle",
            "Car",
            "Car",
            "Car",
            "Yacht",
            "Yacht",
            "Yacht",
        )

        for (i in titleList.indices) {
            val dataEntity = DataEntity(
                null,
                imageList[i],
                titleList[i],
                descList[i],
                categories[i],
                imageList[i],
                phoneNumList[i],
                ratingList[i],
                i
            )
            dataList?.add(dataEntity)
            dataDao.insertData(dataEntity)
        }
    }

    fun addData(item: DataEntity) {
        dataDao.insertData(item)
        notifyDataChanged()
    }

    fun deleteData(item: DataEntity){
        dataDao.delete(item)
    }

    fun getDataById(itemId: Long): DataEntity? {
        return dataDao.getDataById(itemId)
    }

    fun clearData() {
        dataDao.deleteAllData()
    }

    fun registerObserver(observer: DataObserver) {
        dataObserver = observer
    }

    private fun notifyDataChanged() {
        dataObserver?.onDataChanged()
    }

    companion object {
        private var instance: DataRepo? = null

        fun getInstance(context: Context): DataRepo {
            if(instance == null){
                instance = DataRepo(context)
            }
            return instance!!
        }
    }
}