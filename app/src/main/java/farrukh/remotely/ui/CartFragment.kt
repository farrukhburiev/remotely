package farrukh.remotely.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import farrukh.remotely.R
import farrukh.remotely.adapter.CartproductAdapter
import farrukh.remotely.database.AppDataBase
import farrukh.remotely.databinding.FragmentCardBinding
import farrukh.remotely.model.CartData
import farrukh.remotely.model.CartProduct
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
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment() {
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
        val binding = FragmentCardBinding.inflate(inflater,container,false)
        val user = appDatabase.getUserDao().getUser().get(appDatabase.getUserDao().getUser().size-1)
        val api = APIClient.getInstance().create(APIService::class.java)
        var cart_products = mutableListOf<CartProduct>()

        Log.d("TAG", "onCreateView: ${appDatabase.getUserDao().getUser().get(appDatabase.getUserDao().getUser().size-1).name}")

        api.getCartsOfUser(user.id_user!!).enqueue(object :Callback<CartData>{
            override fun onResponse(call: Call<CartData>, response: Response<CartData>) {
                for (i in response.body()!!.carts[0].products){
                    cart_products.add(i)
                }

                var adapter = CartproductAdapter(cart_products,object :CartproductAdapter.ItemClick{
                    override fun OnItemClick(product: CartProduct) {

                    }
                })
                var layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.VERTICAL,false)

                binding.cartProduct.layoutManager = layoutManager
                binding.cartProduct.adapter = adapter


                Log.d("TAG", "onResponse:${cart_products} ")
            }

            override fun onFailure(call: Call<CartData>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}