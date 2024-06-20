package com.example.companyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.companyapp.model.CompanyModel

class CompanyViewModel : ViewModel() {
    private val _companies = MutableLiveData<MutableList<CompanyModel>>(mutableListOf(CompanyModel("", "", "")))
    val companies: LiveData<MutableList<CompanyModel>> get() = _companies

    fun addCompany() {
        _companies.value?.add(CompanyModel("", "", ""))
        _companies.value = _companies.value
    }

    fun removeCompany(index: Int) {
        if (index != 0 && _companies.value!!.size > 1) {
            _companies.value?.removeAt(index)
            _companies.value = _companies.value
        }
    }

    fun updateCompany(index: Int, company: CompanyModel) {
        _companies.value?.set(index, company)
        _companies.value = _companies.value
    }

    fun submitCompanies(): Boolean {
        _companies.value?.let {
            for (company in it) {
                if (company.name.isEmpty() || !isValidEmail(company.email) || !isValidPhone(company.phone)) {
                    return false
                }
            }
            return true
        }
        return false
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPhone(phone: String): Boolean {
        return android.util.Patterns.PHONE.matcher(phone).matches()
    }
}