package mobile.eltd.android.tpagatest.core

enum class Route(val route: String) {
    LOGIN("login"),
    HOME("home"),
    MOVEMENT_DETAIL("movement_detail/{movementId}"),
}

fun Route.movementDetail(movementId: Int): String = "movement_detail/$movementId"