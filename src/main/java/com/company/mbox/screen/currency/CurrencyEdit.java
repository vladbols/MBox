package com.company.mbox.screen.currency;

import io.jmix.ui.screen.*;
import com.company.mbox.entity.Currency;

@UiController("Currency.edit")
@UiDescriptor("currency-edit.xml")
@EditedEntityContainer("currencyDc")
public class CurrencyEdit extends StandardEditor<Currency> {
}