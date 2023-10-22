package farrukh.remotely.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import farrukh.remotely.R
import farrukh.remotely.adapter.ProductAdapter
import farrukh.remotely.database.AppDataBase
import farrukh.remotely.database.entity.UserData
import farrukh.remotely.databinding.FragmentHomeBinding
import farrukh.remotely.model.Product
import farrukh.remotely.model.ProductData
import farrukh.remotely.networking.APIClient
import farrukh.remotely.networking.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG = "TAG"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var discount_products: MutableList<Product>

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
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        discount_products = mutableListOf()
        val api = APIClient.getInstance().create(APIService::class.java)

        if (appDatabase.getUserDao().getUser().size == 0){
            appDatabase.getUserDao().addUser(UserData(id_user = 0, name = "", password = ""))
        }
          var user = appDatabase.getUserDao().getUser()
        api.getAllProducts().enqueue(object : Callback<ProductData> {
            override fun onResponse(call: Call<ProductData>, response: Response<ProductData>) {
//                Log.d(TAG, "onResponse: ")
                if (response.isSuccessful && response.body() != null){
                    discount_products = response.body()!!.products.toMutableList()
                    Log.d(TAG, "onResponse: $discount_products")

//
                    var product_adapter =
                        ProductAdapter(discount_products, object : ProductAdapter.ItemClick {
                            override fun OnItemClick(product: Product) {

                                parentFragmentManager.beginTransaction().replace(R.id.main,View_ItemFragment.newInstance(product)).addToBackStack("Home").commit()
                            }

                        })
                    var manager =
                        GridLayoutManager(requireContext(),2,LinearLayoutManager.VERTICAL,false)



                    binding.discountRv.layoutManager = manager
                    binding.discountRv.adapter = product_adapter



                }


            }

            override fun onFailure(call: Call<ProductData>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
            }

        })

        binding.search.setOnClickListener{
//            binding.search.visibility = View.INVISIBLE
//            binding.cart.visibility = View.INVISIBLE
            parentFragmentManager.beginTransaction().replace(R.id.main,SearchFragment()).setReorderingAllowed(true).addToBackStack("Home").commit()
        }

        binding.cart.setOnClickListener {
            if (user.get(user.size-1).name == ""){
                parentFragmentManager.beginTransaction().replace(R.id.main, LoginFragment())
                    .addToBackStack("Home").commit()
            }

            else parentFragmentManager.beginTransaction().replace(R.id.main, CartFragment())
                .addToBackStack("Home").commit()



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
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}