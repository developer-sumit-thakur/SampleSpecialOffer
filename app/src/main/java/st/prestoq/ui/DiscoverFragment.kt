package st.prestoq.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import st.prestoq.R
import st.prestoq.adapter.ListItemsAdapter
import st.prestoq.viewmodel.model.ApiResponse
import st.prestoq.viewmodel.model.SpecialsViewModel

class DiscoverFragment : Fragment() {
    companion object {
        val TAG: String = "DiscoverFragment"
    }

    lateinit var adapter: ListItemsAdapter
    lateinit var recyclerView: RecyclerView
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    var specialsViewModel: SpecialsViewModel? = null
    var fragmentListener: FragmentListener? = null

    interface FragmentListener {
        fun onSuccess()
        fun onError()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (activity is FragmentListener) {
            fragmentListener = activity as FragmentListener
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        specialsViewModel = ViewModelProviders.of(this).get(SpecialsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.frag_manager_specials, container, false)

        recyclerView = view.findViewById(R.id.special_list)
        recyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(activity)
        recyclerView.setLayoutManager(mLayoutManager)

        adapter = ListItemsAdapter(ArrayList())
        recyclerView.setAdapter(adapter)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        specialsViewModel?.responseLiveData?.observe(this, Observer {
            it?.apply { showUI(it) } ?: showError()
        })

    }

    override fun onDetach() {
        super.onDetach()
        fragmentListener = null
    }

    fun showError() {
        Log.d(TAG, "Error in getting response")
        fragmentListener?.onError()
    }

    fun showUI(result: ApiResponse) {
        Log.d(TAG, "Response : $result")
        fragmentListener?.onSuccess()

        result.managerSpecials?.apply {
            adapter?.setSpecials(this)
        }
    }
}