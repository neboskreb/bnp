package john.pazekha.bnp

import john.pazekha.bnp.controller.IController
import john.pazekha.bnp.controller.impl.ControllerImpl
import john.pazekha.bnp.logic.IGameLogic
import john.pazekha.bnp.logic.impl.GameLogicImpl

object ClassFactory {
    private var logic: IGameLogic? = null
    private var controller: IController? = null

    @Synchronized
    fun setController(impl: IController?) {
        controller = impl
    }

    @Synchronized
    fun createController() : IController {
        if (controller == null) {
            controller = ControllerImpl()
        }
        return controller as IController
    }

    @Synchronized
    fun setGameLogic(impl: IGameLogic?) {
        logic = impl
    }

    @Synchronized
    fun createGameLogic(): IGameLogic {
        if (logic == null) {
            logic = GameLogicImpl()
        }
        return logic as IGameLogic
    }
}