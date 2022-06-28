package com.company.mbox.screen.currency;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.Currency;

@UiController("Currency.browse")
@UiDescriptor("currency-browse.xml")
@LookupComponent("currenciesTable")
public class CurrencyBrowse extends StandardLookup<Currency> {
}