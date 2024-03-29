package com.example.and_diploma_shaba.activity


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import com.example.and_diploma_shaba.R
import com.example.and_diploma_shaba.activity.utils.SectionsPagerAdapter
import com.example.and_diploma_shaba.activity.utils.TAB_ICONS
import com.example.and_diploma_shaba.activity.utils.TAB_TITLES
import com.example.and_diploma_shaba.databinding.FragmentMainBinding
import com.example.and_diploma_shaba.repository.AppEntities
import com.example.and_diploma_shaba.util.ZoomOutPageTransformer
import com.example.and_diploma_shaba.viewmodel.AuthViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by activityViewModels()

    @Inject
    lateinit var repo: AppEntities

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.app_name)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity?.findViewById<Toolbar>(R.id.toolbar))?.setNavigationIcon(R.drawable.ic_baseline_more_vert_24)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pager.adapter = SectionsPagerAdapter(this)
        binding.pager.setOffscreenPageLimit(1)

        val clickCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                repo.saveViewPagerPageToPrefs(position)
            }
        }

        binding.pager.registerOnPageChangeCallback(clickCallback)

        TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
            tab.setText(TAB_TITLES[position])
            tab.setIcon(TAB_ICONS[position])
        }.attach()

        binding.pager.setCurrentItem(repo.getSavedViewPagerPage(), false)
        binding.pager.setPageTransformer(ZoomOutPageTransformer())


        viewModel.logined.observe(viewLifecycleOwner) { item ->
            binding.pager.currentItem = 0
        }

        childFragmentManager.setFragmentResultListener(
            "keyMainFragment",
            viewLifecycleOwner
        ) { requestKey, bundle ->
          setFragmentResult(requestKey, bundle)
        }

        childFragmentManager.setFragmentResultListener(
            "keyEvents",
            viewLifecycleOwner
        ) { requestKey, bundle ->
            setFragmentResult(requestKey, bundle)
        }

        childFragmentManager.setFragmentResultListener(
            "keyWall",
            viewLifecycleOwner
        ) { requestKey, bundle ->
            setFragmentResult(requestKey, bundle)
        }
//
//        childFragmentManager.setFragmentResultListener(
//            "keyJobs",
//            viewLifecycleOwner
//        ) { requestKey, bundle ->
//            setFragmentResult(requestKey, bundle)
//        }
//        childFragmentManager.setFragmentResultListener(
//            "keyNewJob",
//            viewLifecycleOwner
//        ) { requestKey, bundle ->
//            setFragmentResult(requestKey, bundle)
//        }
//
        childFragmentManager.setFragmentResultListener(
            "keyNewPost",
            viewLifecycleOwner
        ) { requestKey, bundle ->
           setFragmentResult(requestKey, bundle)
        }

        childFragmentManager.setFragmentResultListener(
            "keyNewEvent",
            viewLifecycleOwner
        ) { requestKey, bundle ->
            setFragmentResult(requestKey, bundle)
        }
//
//        childFragmentManager.setFragmentResultListener(
//            "keyMapPicker",
//            viewLifecycleOwner
//        ) { requestKey, bundle ->
//            setFragmentResult(requestKey, bundle)
//        }





    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
