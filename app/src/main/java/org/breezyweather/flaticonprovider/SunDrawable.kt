/**
 * This file is part of Breezy Weather Flat Icon Provider.
 *
 * Breezy Weather Flat Icon Provider is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, version 3 of the License.
 *
 * Breezy Weather Flat Icon Provider is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Breezy Weather Flat Icon Provider. If not, see <https://www.gnu.org/licenses/>.
 */

package org.breezyweather.flaticonprovider

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import kotlin.math.min


class SunDrawable : Drawable() {
    private val mPaint = Paint().apply {
        isAntiAlias = true
    }

    private val mColor: Int = Color.rgb(255, 184, 62)
    private var mAlpha: Float = 1f
    private var mBounds: Rect
    private var mCoreRadius = 0f
    private var mCX = 0f
    private var mCY = 0f

    private var haloHeight = 0f
    private var haloMargins = 0f
    private var haloRadius = 0f
    private val haloRectF = RectF()
    private var haloWidth = 0f

    init {
        mBounds = bounds
        ensurePosition(mBounds)
    }

    private fun ensurePosition(bounds: Rect) {
        val min = min(bounds.width(), bounds.height())
        this.mCoreRadius = 0.4843f * min / 2.0f
        val width = bounds.width().toDouble()
        val left = bounds.left.toDouble()
        this.mCX = (width * 1.0 / 2.0 + left).toFloat()
        val height = bounds.height().toDouble()
        val top = bounds.top.toDouble()
        this.mCY = (height * 1.0 / 2.0 + top).toFloat()
        haloWidth = 0.0703f * min
        haloHeight = 0.1367f * min
        haloRadius = haloWidth / 2.0f
        haloMargins = min * 0.0898f
    }

    override fun onBoundsChange(bounds: Rect) {
        mBounds = bounds
        ensurePosition(bounds)
    }

    override fun draw(canvas: Canvas) {
        mPaint.setAlpha((mAlpha * 255.0f).toInt())
        mPaint.setColor(mColor)
        for (i in 0..3) {
            val save = canvas.save()
            canvas.rotate((i * 45).toFloat(), mCX, mCY)
            haloRectF.set(
                mCX - haloWidth / 2.0f,
                mCX - mCoreRadius - haloHeight - haloMargins,
                haloWidth / 2.0f + mCX,
                mCX - mCoreRadius - haloHeight - haloMargins + haloHeight
            )
            canvas.drawRoundRect(haloRectF, haloRadius, haloRadius, mPaint)
            haloRectF.set(
                mCX - haloWidth / 2.0f,
                mCX + mCoreRadius + haloMargins,
                haloWidth / 2.0f + mCX,
                mCX + mCoreRadius + haloMargins + haloHeight
            )
            canvas.drawRoundRect(haloRectF, haloRadius, haloRadius, mPaint)
            canvas.restoreToCount(save)
        }
        canvas.drawCircle(mCX, mCY, mCoreRadius, mPaint)
    }

    override fun setAlpha(alpha: Int) {
        mAlpha = alpha.toFloat()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.setColorFilter(colorFilter)
    }

    @Deprecated("Deprecated in Java")
    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

    override fun getIntrinsicWidth(): Int {
        return mBounds.width()
    }

    override fun getIntrinsicHeight(): Int {
        return mBounds.height()
    }
}
