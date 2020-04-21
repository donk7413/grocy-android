package xyz.zedler.patrick.grocy.fragment.bottomSheetDialog;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import xyz.zedler.patrick.grocy.MainActivity;
import xyz.zedler.patrick.grocy.R;
import xyz.zedler.patrick.grocy.api.GrocyApi;
import xyz.zedler.patrick.grocy.model.Location;
import xyz.zedler.patrick.grocy.model.Product;
import xyz.zedler.patrick.grocy.model.ProductGroup;
import xyz.zedler.patrick.grocy.model.QuantityUnit;
import xyz.zedler.patrick.grocy.util.Constants;
import xyz.zedler.patrick.grocy.util.NumUtil;
import xyz.zedler.patrick.grocy.view.ListItem;

public class MasterProductBottomSheetDialogFragment extends BottomSheetDialogFragment {

	private final static boolean DEBUG = false;
	private final static String TAG = "MasterProductBottomSheet";

	private MainActivity activity;
	private Product product;
	private Location location;
	private QuantityUnit quantityUnitPurchase, quantityUnitStock;
	private ProductGroup productGroup;
	private ListItem
			itemName,
			itemLocation,
			itemMinStockAmount,
			itemQuPurchase,
			itemQuStock,
			itemQuFactor,
			itemProductGroup,
			itemBarcodes;

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new BottomSheetDialog(
				requireContext(),
				R.style.Theme_Grocy_BottomSheetDialog
		);
	}

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState
	) {
		View view = inflater.inflate(
				R.layout.fragment_bottomsheet_master_product,
				container,
				false
		);

		activity = (MainActivity) getActivity();
		assert activity != null;

		Bundle bundle = getArguments();
		if(bundle != null) {
			product = bundle.getParcelable(Constants.ARGUMENT.PRODUCT);
			location = bundle.getParcelable(Constants.ARGUMENT.LOCATION);
			quantityUnitPurchase = bundle.getParcelable(Constants.ARGUMENT.QUANTITY_UNIT_PURCHASE);
			quantityUnitStock = bundle.getParcelable(Constants.ARGUMENT.QUANTITY_UNIT_STOCK);
			productGroup = bundle.getParcelable(Constants.ARGUMENT.PRODUCT_GROUP);
		}

		// VIEWS

		itemName = view.findViewById(R.id.item_master_product_name);
		itemLocation = view.findViewById(R.id.item_master_product_location);
		itemMinStockAmount = view.findViewById(R.id.item_master_product_min_stock_amount);
		itemQuPurchase = view.findViewById(R.id.item_master_product_qu_purchase);
		itemQuStock = view.findViewById(R.id.item_master_product_qu_stock);
		itemQuFactor = view.findViewById(R.id.item_master_product_qu_factor);
		itemProductGroup = view.findViewById(R.id.item_master_product_product_group);
		itemBarcodes = view.findViewById(R.id.item_master_product_barcodes);

		if(product.getPictureFileName() != null) {
			Picasso.get().load(
					new GrocyApi(activity).getPicture(product.getPictureFileName(), 300)
			).into((ImageView) view.findViewById(R.id.image_master_product));
		} else {
			view.findViewById(
					R.id.linear_master_product_picture_container
			).setVisibility(View.GONE);
		}

		// TOOLBAR

		MaterialToolbar toolbar = view.findViewById(R.id.toolbar_master_product);
		toolbar.setOnMenuItemClickListener(item -> {
			switch (item.getItemId()) {
				case R.id.action_edit:
					/*((StockFragment) activity.getCurrentFragment()).performAction(
							Constants.ACTION.CONSUME_ALL,
							product.getId()
					);*/
					dismiss();
					return true;
				case R.id.action_consume_spoiled:
					/*((StockFragment) activity.getCurrentFragment()).performAction(
							Constants.ACTION.CONSUME_SPOILED,
							product.getId()
					);*/
					dismiss();
					return true;
			}
			return false;
		});

		setData();

		return view;
	}

	private void setData() {

		// NAME
		itemName.setText(
				activity.getString(R.string.property_name),
				product.getName()
		);

		// LOCATION
		itemLocation.setText(
				activity.getString(R.string.property_location),
				location.getName()
		);

		// MIN STOCK AMOUNT
		itemMinStockAmount.setText(
				activity.getString(R.string.property_amount_min_stock),
				NumUtil.trim(product.getMinStockAmount())
		);

		// QUANTITY UNIT PURCHASE
		itemQuPurchase.setText(
				activity.getString(R.string.property_qu_purchase),
				quantityUnitPurchase.getName()
		);

		// QUANTITY UNIT STOCK
		itemQuStock.setText(
				activity.getString(R.string.property_qu_stock),
				quantityUnitStock.getName()
		);

		// QUANTITY UNIT STOCK
		itemQuFactor.setText(
				activity.getString(R.string.property_qu_factor),
				NumUtil.trim(product.getQuFactorPurchaseToStock())
		);

		// PRODUCT GROUP
		if(product.getProductGroupId() != null && productGroup != null) {
			itemProductGroup.setText(
					activity.getString(R.string.property_product_group),
					productGroup.getName()
			);
		} else {
			itemProductGroup.setVisibility(View.GONE);
		}

		// BARCODES
		if(product.getBarcode() != null && !product.getBarcode().trim().equals("")) {
			itemBarcodes.setText(
					activity.getString(R.string.property_barcodes),
					TextUtils.join(", ", product.getBarcode().split(","))
			);
		} else {
			itemBarcodes.setVisibility(View.GONE);
		}
	}

	@NonNull
	@Override
	public String toString() {
		return TAG;
	}
}