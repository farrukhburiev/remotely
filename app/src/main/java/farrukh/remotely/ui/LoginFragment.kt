package farrukh.remotely.ui


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import farrukh.remotely.R
import farrukh.remotely.database.AppDataBase
import farrukh.remotely.database.entity.UserData
import farrukh.remotely.databinding.FragmentLoginBinding
import farrukh.remotely.model.Login
import farrukh.remotely.model.User
import farrukh.remotely.networking.APIClient
import farrukh.remotely.networking.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    val appDatabase: AppDataBase by lazy {
        AppDataBase.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)

        val api = APIClient.getInstance().create(APIService::class.java)

        var users = appDatabase.getUserDao().getUser()




        binding.continueBtn.setOnClickListener {
            var l = Login(binding.loginOrg.text.toString().trim(), binding.passwordOrg.text.toString().trim())
            api.login(l).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful && response.body() != null) {
                        appDatabase.getUserDao().addUser(
                            UserData(
                                id_user = response.body()!!.id,
                                name = binding.loginOrg.text.toString(),
                                password = binding.passwordOrg.text.toString()
                            )
                        )

                        users = appDatabase.getUserDao().getUser()
                        Log.d("TAG", "onResponse: ${users.get(users.size-1).name+users.get(users.size-1).id_user}")
                        parentFragmentManager.beginTransaction().replace(R.id.main,CartFragment()).commit()

                    }

                    else Toast.makeText(requireContext(), "you have not signed up", Toast.LENGTH_SHORT).show()

//                        if (response.body().pa)
//                    Log.d("TAG", "onResponse: ${binding.userText.text.toString()+response.body()!!.username}")

                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d("TAG", "onFailure: $t")
                }

            })
        }


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}