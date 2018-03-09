package me.rozkmin.livetram

/**
 * Created by jaroslawmichalik on 16.12.2017
 */
interface BaseView {
    fun showLoading()
    fun hideLoading()
    fun displayError(throwable: Throwable = Throwable())
}

interface BasePresenter<in BaseView> {
    fun attach(view: BaseView)
    fun detach()
}