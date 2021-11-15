package com.fragnostic.i18n.service.i18n

import com.fragnostic.i18n.impl.AbstractSingleMessageI18n

class I18nServerServiceI18n extends AbstractSingleMessageI18n {

  override def baseDir: String = "FRAGNOSTIC_I18N_BASE_DIR"

  override def baseName: String = "FRAGNOSTIC_I18N_BASE_NAME"

}
