package com.toyourstore.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.toyourstore.R
import com.toyourstore.preference.SessionData
import de.footprinttech.wms.db.DataBaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileFragment : BaseFragment() {

    private var rootView: View? = null
    private lateinit var edtUser: EditText
    private lateinit var edtContactNo: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtAddress: EditText


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_profile, container, false)
        edtUser = rootView?.findViewById(R.id.edtUser)!!
        edtContactNo = rootView?.findViewById(R.id.edtContactNo)!!
        edtEmail = rootView?.findViewById(R.id.edtEmail)!!
        edtAddress = rootView?.findViewById(R.id.edtAddress)!!
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle("Profile")
        hideBack()
        showMenu()
        hideButtomBar()
        setDefaultValue()
    }

    private fun setDefaultValue() {
        lifecycleScope.launch(IO) {
            val user = DataBaseHelper.getDatabaseDao(requireContext())
                    .getUser(SessionData.getInstance(requireContext()).getUserId()!!.toLong())
            withContext(Main) {
                edtUser.setText(user.name)
                edtContactNo.setText(user.contact)
                edtEmail.setText(user.email)
            }
        }

    }


}