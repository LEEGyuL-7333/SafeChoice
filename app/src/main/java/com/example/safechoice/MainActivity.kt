package com.example.safechoice

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 에지 투 엣지 기본 적용
        enableEdgeToEdge()
        // 시스템 윈도우 자동 맞춤 비활성화(우리가 직접 인셋 적용)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContentView(R.layout.activity_main)

        // 상태바 아이콘 색상(라이트/다크) 제어: 배경이 밝다면 true
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = true   // 밝은 배경 위 검은 아이콘
            isAppearanceLightNavigationBars = true
        }

        // 루트 뷰에만 인셋 적용 (id가 main인 루트 컨테이너라고 가정)
        val root = findViewById<android.view.View>(R.id.main)
        if (root != null) {
            ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                // 좌/우/상단은 그대로, 하단은 제스처 내비게이션 높이만큼만
                v.updatePadding(
                    left = systemBars.left,
                    top = systemBars.top,
                    right = systemBars.right,
                    bottom = systemBars.bottom
                )
                // 우리가 소비하지 않고 그대로 전달(자식이 추가로 쓸 수 있게)
                insets
            }
            // 최초 인셋 적용 트리거
            ViewCompat.requestApplyInsets(root)
        }
    }
}
