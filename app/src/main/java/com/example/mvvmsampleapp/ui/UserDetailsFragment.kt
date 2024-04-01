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
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mvvmsampleapp.databinding.FragmentUserDetailBinding
import com.example.mvvmsampleapp.model.room.AppDatabase
import com.example.mvvmsampleapp.repository.UserRepository
import com.example.mvvmsampleapp.retrofit.ApiHelper
import com.example.mvvmsampleapp.utils.NetworkConnection
import com.example.mvvmsampleapp.utils.ViewModelFactory
import com.example.mvvmsampleapp.viewmodel.UserDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint
class UserDetailsFragment : Fragment() {
    private val args by navArgs<UserDetailsFragmentArgs>()

    private var _binding: FragmentUserDetailBinding? = null
    private lateinit var viewModel: UserDetailViewModel

    @Inject
    lateinit var apiHelper: ApiHelper

    @Inject
    lateinit var networkConnection:NetworkConnection
    @Inject
    lateinit var database: AppDatabase
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //intialize viewmodel
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserRepository(apiHelper), database, networkConnection)
        )[UserDetailViewModel::class.java]
        lifecycleScope.launch {
            viewModel.getUserDetails(args.id.toInt())
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        lifecycleScope.launch {
            viewModel.userDetailResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    Glide.with(requireContext()).load(it.avatar)
                        .into(binding.imageView)
                    binding.tvAuthor.text = it.firstName
                    binding.tvTitle.text = it.lastName
                    binding.tvDesc.text = it.email
                    binding.tvWebUrl.text = it.id.toString()
                    binding.backButton.visibility=View.VISIBLE
                }
            }
            viewModel.errorData.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}