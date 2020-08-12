package com.android.app_aqi.list

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.app_aqi.R
import com.android.app_aqi.SharedViewModel
import com.android.app_aqi.add.SiteListDialogFragment

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
        exitTransition = TransitionInflater.from(context).inflateTransition(R.transition.list_to_pager_exit_transition)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        listRecyclerView = view.findViewById(R.id.rv_list)
        return view
    }

    /**
     * Scrolls the recycler view to show the last viewed item in the grid. This is important when
     * navigating back from the grid.
     */
    private fun scrollToPosition() {
        listRecyclerView.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(v: View,
                                        left: Int,
                                        top: Int,
                                        right: Int,
                                        bottom: Int,
                                        oldLeft: Int,
                                        oldTop: Int,
                                        oldRight: Int,
                                        oldBottom: Int) {
                listRecyclerView.removeOnLayoutChangeListener(this)
                val listLayoutManager = listRecyclerView.layoutManager
                val viewAtPosition = listLayoutManager?.findViewByPosition(sharedViewModel.currentPos)
                // Scroll to position if the view for the current position is null (not currently part of
                // layout manager children), or it's not completely visible.
                if (viewAtPosition == null || listLayoutManager.isViewPartiallyVisible(viewAtPosition, false, true)) {
                    listRecyclerView.post(Runnable { listLayoutManager?.scrollToPosition(sharedViewModel.currentPos)})
                }
            }
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        scrollToPosition()
        prepareTransitions()
    }

    private fun prepareTransitions() {
        setExitSharedElementCallback(object: androidx.core.app.SharedElementCallback(){
            override fun onMapSharedElements(names: MutableList<String>?, sharedElements: MutableMap<String, View>?) {
                // Locate the ViewHolder for the clicked position.
                val selectedViewHolder: RecyclerView.ViewHolder = listRecyclerView
                        .findViewHolderForAdapterPosition(sharedViewModel.currentPos)
                        ?: return

                // Map the first shared element name to the child ImageView.
                sharedElements!![names!![0]]= selectedViewHolder.itemView.findViewById(R.id.list_item_root)
            }
        })
    }

    private fun initRecyclerView() {
        sharedViewModel.allFollowedSite.value?.let{
            listRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            listRecyclerView.adapter?.notifyDataSetChanged()
            listRecyclerView.adapter = SiteListAdapter(it, this)
        }
    }

    override fun onItemClick(itemView: View, position: Int) {
        listenerList?.onListItemClick(itemView, position)
    }

    override fun onAddClick(footRoot: CardView) {
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        val prev = parentFragmentManager.findFragmentByTag(SiteListDialogFragment::class.simpleName)
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
        val dialogFragment = SiteListDialogFragment()
        dialogFragment.setOnDismissListener(DialogInterface.OnDismissListener {
            initRecyclerView()
        })
        dialogFragment.show(ft, SiteListDialogFragment::class.simpleName)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListItemClickListener) {
            listenerList = context
        } else {
            throw RuntimeException("$context must implement OnListItemClickListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listenerList = null
    }


    interface OnListItemClickListener {
        fun onListItemClick(itemView: View, position: Int)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}
