package john.pazekha.bnp.view.impl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import john.pazekha.bnp.BnpApp
import john.pazekha.bnp.ClassFactory
import john.pazekha.bnp.R
import john.pazekha.bnp.controller.IController
import john.pazekha.bnp.databinding.MainFragmentBinding
import john.pazekha.bnp.logic.IGameLogic

class MainFragment(private val controller: IController, private val logic: IGameLogic) : Fragment() {

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment(ClassFactory.createController(), BnpApp.app.getGameLogic())
        }
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val binding : MainFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.setController(controller)
        controller.setView(viewModel)
        controller.setLogic(logic)
        controller.resume()
    }

}
