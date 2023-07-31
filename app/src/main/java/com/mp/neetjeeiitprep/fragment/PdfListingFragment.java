package com.mp.neetjeeiitprep.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mp.neetjeeiitprep.AppUtils.AppUtils;
import com.mp.neetjeeiitprep.Constant;
import com.mp.neetjeeiitprep.R;
import com.mp.neetjeeiitprep.adapter.PdfListingAdapter;
import com.mp.neetjeeiitprep.dataModel.PdfListDto;
import com.mp.neetjeeiitprep.dataModel.PdfListNcertDataModel;
import com.mp.neetjeeiitprep.dataModel.PdfListPrevYearDataModel;
import com.mp.neetjeeiitprep.network.ApiClient;
import com.mp.neetjeeiitprep.network.WebApi;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PdfListingFragment extends Fragment {

    private View rootView;
    private Bundle navigationData;
    //private OnSubjectClickListener onSubjectClickListener;
    private PdfListingAdapter pdfListingAdapter;
    private ArrayList<PdfListDto> pdfList;
    private ProgressDialog pd;
    private String pdfUrl;
    private String TAG = "ChapterListFragmentTAG";

    public PdfListingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_pdf_listing, container, false);

        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading...");
        navigationData = getArguments();

        String catId = getArguments().getString(Constant.CATEGORY_ID);
        String typeId = getArguments().getString(Constant.SELECTED_TYPE_ID);
        String pdfType = getArguments().getString(Constant.PDF_LIST_TYPE);

        pdfList = new ArrayList<>();
        RecyclerView rcvPdfList = rootView.findViewById(R.id.rcvPdfList);
        rcvPdfList.setLayoutManager(new LinearLayoutManager(getContext()));
        pdfListingAdapter = new PdfListingAdapter(getContext(), pdfList, navigationData.getInt(Constant.SUBJECT_ICON), getArguments());
        rcvPdfList.setAdapter(pdfListingAdapter);

        if (AppUtils.isOnline(getContext())) {
            fetchPdfList(typeId, pdfType);
        } else {
            Toast.makeText(getContext(), "check your internet connection", Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }

    private void fetchPdfList(String subCatId, final String type) {
        pd.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("type_id", subCatId);
        params.put("type", type);

        WebApi webApi = ApiClient.getApiInstance().create(WebApi.class);
        Call<JsonElement> callApi = webApi.getPdfList(params);
        callApi.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
           //     Log.d(TAG, "fetchPdfList onResponse: " + response.body().toString());
                loadPdfList(response, type);
                pd.dismiss();
                //  dataFetched = true;
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e(TAG, "fetchPdfList onFailure: " + t.toString());
                pd.dismiss();
            }
        });

    }

    private void loadPdfList(Response<JsonElement> response, String type) {

        if (response.isSuccessful()) {
            if (type == Constant.PDF_LIST_NCERT) {
                PdfListNcertDataModel mPdfListNcertDataModel = new Gson().fromJson(response.body().getAsJsonObject(), PdfListNcertDataModel.class);
                // JsonObject jobj=response.body().getAsJsonObject();
                if (mPdfListNcertDataModel.getCode().contentEquals(Constant.CODE_SUCCESS)) {
                    if (mPdfListNcertDataModel.getPdlist().size() > 0) {
                        for (int i = 0; i < mPdfListNcertDataModel.getPdlist().size(); i++) {
                            pdfList.add((new PdfListDto(
                                    mPdfListNcertDataModel.getPdlist().get(i).getNcertId(),
                                    mPdfListNcertDataModel.getPdlist().get(i).getFilenames(),
                                    mPdfListNcertDataModel.getPdlist().get(i).getNcertFileName(),
                                    mPdfListNcertDataModel.getPdfPath() + mPdfListNcertDataModel.getPdlist().get(i).getNcertFileName()
                            )));
                        }

                        pdfListingAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "no Pdf found", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                PdfListPrevYearDataModel mPdfListPrevYearDataModel = new Gson().fromJson(response.body().getAsJsonObject(), PdfListPrevYearDataModel.class);
                if (mPdfListPrevYearDataModel.getCode().contentEquals(Constant.CODE_SUCCESS)) {
                    if (mPdfListPrevYearDataModel.getPdlist().size() > 0) {
                        for (int i = 0; i < mPdfListPrevYearDataModel.getPdlist().size(); i++) {
                            pdfList.add((new PdfListDto(
                                    mPdfListPrevYearDataModel.getPdlist().get(i).getPdfId(),
                                    mPdfListPrevYearDataModel.getPdlist().get(i).getFilenames(),
                                    mPdfListPrevYearDataModel.getPdlist().get(i).getPreYrFile(),
                                    mPdfListPrevYearDataModel.getPdfPath() + mPdfListPrevYearDataModel.getPdlist().get(i).getPreYrFile()
                            )));
                        }

                        pdfListingAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "no Pdf found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
