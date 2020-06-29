package com.android.app_aqi.list

import android.content.Context
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.app_aqi.R
import com.android.app_aqi.SharedViewModel
import com.android.app_aqi.main.MainActivity

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ListFragment.OnListItemClickListener] interface
 * to handle interaction events.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment(), SiteListAdapter.ItemClickListener {
    private var listenerList: OnListItemClickListener? = null
    private lateinit var listRecyclerView :RecyclerView
    private lateinit var sharedViewModel : SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        exitTransition = TransitionInflater.from(context)
//                .inflateTransition(R.transition.list_to_pager_exit_transition)
        setExitSharedElementCallback(object: androidx.core.app.SharedElementCallback(){
            override fun onMapSharedElements(names: MutableList<String>?, sharedElements: MutableMap<String, View>?) {
                val view = listRecyclerView.findViewHolderForAdapterPosition(sharedViewModel.currentPos)?.itemView
                val item = sharedViewModel.siteList[sharedViewModel.currentPos].siteName
                if(view == null) return
                names?.clear()
                sharedElements?.clear()
                item?.let {
                    names?.add(it)
                    sharedElements?.put(it, view)
                }
            }
        })

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_list, container, false)
        listRecyclerView = view.findViewById(R.id.rv_list)
        initRecyclerView()
        return view
    }

    private fun initRecyclerView() {
        listRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listRecyclerView.adapter = SiteListAdapter((activity as MainActivity).sharedViewModel.siteList, this)
    }

    override fun onItemClick(itemView: View, position: Int) {
        listenerList?.onListItemClick(itemView, position)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListItemClickListener) {
            listenerList = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listenerList = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListItemClickListener {
        fun onListItemClick(itemView: View, position: Int)
    }

    companion object {
        @JvmStatic
        fun newInstance() =ListFragment()
    }
}
