package john.pazekha.bnp

import android.app.Application
import john.pazekha.bnp.logic.IGameLogic

class BnpApp: Application() {
    companion object Instance {
        lateinit var app: BnpApp
    }

    private val gameLogic: IGameLogic = ClassFactory.createGameLogic()

    override fun onCreate() {
        app = this
        super.onCreate()
    }

    fun getGameLogic(): IGameLogic {
        return gameLogic
    }

}