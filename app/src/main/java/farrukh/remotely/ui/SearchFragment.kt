package farrukh.remotely.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import farrukh.remotely.R
import farrukh.remotely.adapter.CategoriesAdapter
import farrukh.remotely.adapter.ProductAdapter
import farrukh.remotely.database.AppDataBase
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
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
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

        var products = mutableListOf<Product>()
        var binding =
            farrukh.remotely.databinding.FragmentSearchBinding.inflate(inflater, container, false)



        var layoutManager = GridLayoutManager(requireContext(),2,LinearLayoutManager.HORIZONTAL,false)
        val api = APIClient.getInstance().create(APIService::class.java)
        var categories = mutableListOf<String>()
        api.getAllCategories().enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                for (i in 0 until response.body()!!.size) {
                    categories.add(response.body()!!.get(i))
                }



                if (categories.isNotEmpty()) {
                    var adapter =
                        CategoriesAdapter(
                            categories,
                            requireContext(),
                            object : CategoriesAdapter.ItemClick {
                                override fun OnItemClick(category: String) {
                                    products.clear()

                                    api.getProductsofCategory(category)
                                        .enqueue(object : Callback<ProductData> {
                                            override fun onResponse(
                                                call: Call<ProductData>,
                                                response: Response<ProductData>
                                            ) {
                                                for (i in response.body()!!.products) {
                                                    products.add(i)
                                                }

                                                var adapter = ProductAdapter(products,
                                                    object : ProductAdapter.ItemClick {
                                                override fun OnItemClick(product: Product) {
                                                    parentFragmentManager.beginTransaction().replace(
                                                        R.id.main,View_ItemFragment.newInstance(product)).addToBackStack("Home").commit()

                                                }

                                            })

                                            binding.productsRv.adapter = adapter
                                            binding.productsRv.layoutManager = layoutManager



//                                            Log.d(TAG, "onResponse: ${response.body()}")
                                        }

                                        override fun onFailure(
                                            call: Call<ProductData>,
                                            t: Throwable
                                        ) {
                                            TODO("Not yet implemented")
                                        }

                                    })
                                }

                            })

                    var manager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    binding.categoryRv.layoutManager = manager
                    binding.categoryRv.adapter = adapter
                }


            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
            }

        })
        var searched_products = mutableListOf<Product>()
        binding.sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText !=null) {
                    searched_products.clear()
                    api.searchByName(newText).enqueue(object :Callback<ProductData>{
                        override fun onResponse(
                            call: Call<ProductData>,
                            response: Response<ProductData>
                        ) {
                            for (i in response.body()!!.products){
                                if (i.title.toLowerCase().contains(newText.toLowerCase())){
                                    searched_products.add(i)

                                }

                            }


                            if (searched_products.size == 0){
                                binding.nothingLottie.visibility = View.VISIBLE
                            }
                            else binding.nothingLottie.visibility = View.GONE
                            var adapter = ProductAdapter(searched_products,object :ProductAdapter.ItemClick{
                                override fun OnItemClick(product: Product) {
                                    parentFragmentManager.beginTransaction().replace(R.id.main,View_ItemFragment()).addToBackStack("Search").commit()
                                }

                            })
                            binding.productsRv.layoutManager = layoutManager
                            binding.productsRv.adapter = adapter



                        }

                        override fun onFailure(call: Call<ProductData>, t: Throwable) {
                            Log.d(TAG, "onFailure: $t")
                        }

                    })
                    return true
                }
                return false
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
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}