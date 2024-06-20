import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.companyapp.databinding.ItemViewCompanyBinding
import com.example.companyapp.model.CompanyModel
import com.example.companyapp.viewmodel.CompanyViewModel

class CompanyAdapter(
    private val viewModel: CompanyViewModel
) : RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder>() {

    inner class CompanyViewHolder(private val binding: ItemViewCompanyBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(company: CompanyModel, index: Int) {
            binding.editName.setText(company.name)
            binding.emailEditText.setText(company.email)
            binding.phoneEditText.setText(company.phone)
            binding.removeButton.visibility = if (index == 0) View.GONE else View.VISIBLE
            binding.removeButton.setOnClickListener {
                viewModel.removeCompany(index)
            }

            binding.editName.doAfterTextChanged {
                company.name = it.toString()
                viewModel.updateCompany(index, company)
            }
            binding.emailEditText.doAfterTextChanged {
                company.email = it.toString()
                viewModel.updateCompany(index, company)
            }
            binding.phoneEditText.doAfterTextChanged {
                company.phone = it.toString()
                viewModel.updateCompany(index, company)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val binding = ItemViewCompanyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompanyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        val company = viewModel.companies.value!![position]
        holder.bind(company, position)
    }

    override fun getItemCount(): Int = viewModel.companies.value!!.size
}
