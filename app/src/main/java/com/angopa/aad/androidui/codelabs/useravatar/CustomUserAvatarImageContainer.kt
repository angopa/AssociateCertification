package com.angopa.aad.androidui.codelabs.useravatar

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.angopa.aad.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import kotlin.math.min

class CustomUserAvatarImageContainer @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, deftStyleArray: Int = 0
) : ConstraintLayout(context, attributeSet, deftStyleArray) {

    private var userColor: UserColor? = null

    private var userAvatarUrl: String? = null

    init {
        val view = LayoutInflater.from(context).inflate(
            R.layout.custom_user_avatar,
            this,
            false
        )
        addView(view)
    }

    fun setUp(user: User) {
        this.userColor = user.avatarColor
        this.userAvatarUrl = user.avatarUrl

        setBadgeToImageView(R.id.top_left_batch, user.topLeftBadge)
        setBadgeToImageView(R.id.top_right_batch, user.topRightBadge)
        setBadgeToImageView(R.id.bottom_right_batch, user.bottomRightBadge)
        setBadgeToImageView(R.id.bottom_left_batch, user.bottomLeftBadge)

        updateUserInfo()
    }

    private fun setBadgeToImageView(imageViewId: Int, badge: Badge) {
        val badgeContainer: ImageView = findViewById(imageViewId)
        setImageDrawable(badgeContainer, findCorrespondingDrawableBadge(badge))
    }

    private fun setImageDrawable(imageView: ImageView, drawable: Int) {
        imageView.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources,
                drawable,
                null
            )
        )
    }

    private fun findCorrespondingDrawableBadge(badge: Badge) =
        when (badge) {
            Badge.GOLD -> R.drawable.shape_round_badge_gold
            Badge.SILVER -> R.drawable.shape_round_badge_silver
            Badge.BRONZE -> R.drawable.shape_round_badge_bronze
            Badge.DIAMOND -> R.drawable.shape_round_badge_diamond
            Badge.PEARL -> R.drawable.shape_round_badge_pearl
        }

    private fun updateUserInfo() {
        userColor?.also {
            userAvatarUrl?.let {
                val avatarImageContainer: ImageView = findViewById(R.id.user_avatar_image)
                Glide.with(context)
                    .asBitmap()
                    .load(userAvatarUrl)
                    .fitCenter()
                    .into(getBitmapImageViewTarget(avatarImageContainer))
            }
        }
    }

    private fun getBitmapImageViewTarget(imageView: ImageView): BitmapImageViewTarget {
        return object : BitmapImageViewTarget(imageView) {
            override fun setResource(bitmap: Bitmap?) {
                bitmap?.let {
                    val roundedBitmapDrawable: RoundedBitmapDrawable =
                        createRoundedBitmapImageDrawableWithBorder(bitmap, userColor, resources)
                    imageView.setImageDrawable(roundedBitmapDrawable)
                }
            }
        }
    }

    private fun createRoundedBitmapImageDrawableWithBorder(
        bitmap: Bitmap,
        color: UserColor?,
        resources: Resources
    ): RoundedBitmapDrawable {
        val bitmapWidthImage = bitmap.width
        val bitmapHeightImage = bitmap.height
        val borderWidthHalfImage = 8

        val bitmapRadiusImage = min(bitmapWidthImage, bitmapHeightImage) / 2
        val bitmapSquareWidthImage = min(bitmapWidthImage, bitmapHeightImage)
        val newBitmapSquareWidthImage = bitmapSquareWidthImage + borderWidthHalfImage

        val roundedImageBitmap = Bitmap.createBitmap(
            newBitmapSquareWidthImage,
            newBitmapSquareWidthImage,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(roundedImageBitmap)
        canvas.drawColor(Color.RED)
        val i: Float = (borderWidthHalfImage + bitmapSquareWidthImage - bitmapWidthImage).toFloat()
        val j: Float = (borderWidthHalfImage + bitmapSquareWidthImage - bitmapHeightImage).toFloat()

        canvas.drawBitmap(bitmap, i, j, null)

        val borderImagePaint = Paint()
        borderImagePaint.style = Paint.Style.STROKE
        borderImagePaint.strokeWidth = (borderWidthHalfImage * 2).toFloat()

        borderImagePaint.color = when (color) {
            UserColor.BLUE -> Color.BLUE
            UserColor.GREEN -> Color.GREEN
            UserColor.RED -> Color.RED
            else -> Color.WHITE
        }

        canvas.drawCircle(
            (canvas.width / 2).toFloat(),
            (canvas.height / 2).toFloat(),
            (newBitmapSquareWidthImage / 2).toFloat(),
            borderImagePaint
        )

        val roundedImageBitmapDrawable =
            RoundedBitmapDrawableFactory.create(resources, roundedImageBitmap)

        roundedImageBitmapDrawable.cornerRadius = bitmapRadiusImage.toFloat()
        roundedImageBitmapDrawable.setAntiAlias(true)
        return roundedImageBitmapDrawable
    }
}