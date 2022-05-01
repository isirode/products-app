package com.isirode.products.deploy.vps.docker

import com.github.dockerjava.api.command.BuildImageResultCallback
import com.github.dockerjava.api.model.BuildResponseItem
import org.apache.logging.log4j.LogManager

class BuildImageCallback : BuildImageResultCallback() {

    companion object {
        private val logger = LogManager.getLogger(this::class.java.name)
    }

    override fun onNext(item: BuildResponseItem) {
        super.onNext(item)
        if (item.stream != null) {
            logger.info(item.stream)
        } else {
            logger.info("${item.id}: ${item.status}")
        }
        if (item.errorDetail != null) {
            logger.error(item.errorDetail?.message)
        }
    }

    override fun onComplete() {
        super.onComplete()
        logger.info("Build done")
    }

    override fun onError(throwable: Throwable?) {
        super.onError(throwable)
        if (throwable != null) {
            logger.error(throwable)
            throw throwable
        }
    }
}