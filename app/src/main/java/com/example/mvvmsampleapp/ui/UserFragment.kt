package com.example.mvvmsampleapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mvvmsampleapp.adapter.UserAdapter
import com.example.mvvmsampleapp.databinding.FragmentUserBinding
import com.example.mvvmsampleapp.model.room.AppDatabase
import com.example.mvvmsampleapp.repository.UserRepository
import com.example.mvvmsampleapp.retrofit.ApiHelper
import com.example.mvvmsampleapp.utils.NetworkConnection
import com.example.mvvmsampleapp.utils.ViewModelFactory
import com.example.mvvmsampleapp.utils.hide
import com.example.mvvmsampleapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding

    @Inject
    lateinit var apiHelper: ApiHelper

    @Inject
    lateinit var database: AppDatabase
    @Inject
    lateinit var networkConnection: NetworkConnection

    private lateinit var viewModel: UserViewModel

    private val adapter = UserAdapter {
       findNavController().navigate(UserFragmentDirections.actionUserFragmentToDetailFragment(it.id.toString()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //intialize viewmodel
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserRepository(apiHelper),database)
        )[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.userResponse.observe(viewLifecycleOwner) {
                it?.let {
                    adapter.submitData(it)
                    binding.progressBar.hide()
                    binding.recyclerView.adapter = adapter
                }
            }
            viewModel.errorData.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
       networkConnection.isConnected.observe(viewLifecycleOwner){
           Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()
       }
        }
}
