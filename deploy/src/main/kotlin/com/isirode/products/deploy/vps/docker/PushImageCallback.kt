package com.isirode.products.deploy.vps.docker

import com.github.dockerjava.api.model.PushResponseItem
import com.github.dockerjava.core.command.PushImageResultCallback
import org.apache.logging.log4j.LogManager

class PushImageCallback : PushImageResultCallback() {

    companion object {
        private val logger = LogManager.getLogger(this::class.java.name)
    }

    override fun onNext(item: PushResponseItem) {
        super.onNext(item)
        logger.info("${item.id}: ${item.status}")
        if (item.errorDetail != null) {
            logger.error(item.errorDetail?.message)
        }
    }

    override fun onComplete() {
        super.onComplete()
        logger.info("Push done")
    }

    override fun onError(throwable: Throwable?) {
        super.onError(throwable)
        if (throwable != null) {
            logger.error(throwable)
            throw throwable
        }
    }
}