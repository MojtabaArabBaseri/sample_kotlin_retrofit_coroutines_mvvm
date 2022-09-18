package ir.millennium.sample_kotlin_retrofit_coroutines_mvvm.data.viewModel

class Resource<T> private constructor(

    val status: Status,
    val data: T?,
    val error: Exception?,
    val errorLogical: Int
) {
    enum class Status {
        SUCCESS, ERROR, LOADING, ERROR_LOGICAL
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null, -1)
        }

        fun <T> error(msg: Exception?, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg, -1)
        }

        fun <T> errorLogical(errorLogical: Int, data: T?): Resource<T> {
            return Resource(Status.ERROR_LOGICAL, data, null, errorLogical)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null, -1)
        }
    }
}