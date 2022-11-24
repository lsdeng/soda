package com.hiy.monbie.core

/**
 * auther: liusaideng
 * created on :  2022/11/24 3:47 下午
 * desc:
 */
enum class PageState(val value: Int, val desc: String) {
    LOADING_OF_BOTTOM(0, "最底部, 适用于初始化loading 和 重新加载"),
    LOADING_OF_TOP(1, " 顶部， 适用与已展示内容的情况 做update相关的请求"),
    Content(2, "展示内容"),
    ERROR(3, "错误展示, 这里可扩展 空白、网络异常、数据异常等"),
}