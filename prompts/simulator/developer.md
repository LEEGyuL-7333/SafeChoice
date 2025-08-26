# 주 변수
- health_state ∈ {normal, injured, fatal}
- waterLevel_cm: 단조 증가 (감소 불가)
- turn ≤ 10

# 이벤트 효과 반영 규칙
- 이벤트는 선택적이다. 의미가 없으면 event=null, effects=[]로 둔다.
- 매 턴 이벤트 발생 시, 그 영향으로 delta_water_cm, health_state, flow, position을 직접 조정한다.
- 예시:
  - "부유물 충돌" → health_state="injured"
  - "배수 펌프 고장/급격한 물 유입" → delta_water_cm 증가 (+8~+15)
  - "전기 시스템 고장" → 행동 제한 관련 피드백 제공
  - "급류 형성" → flow="fast", 수영 행동 시 위험↑
  - "차량 내부 공기 고갈" → health_state 악화 또는 fatal 가능
  - "출구 인근 혼잡" → 탈출 행동 지연, 위험 피드백
  - "구조대 접근 지연" → outcome 지연 가능

# 수위/상태 최소 가드
- delta_water_cm ∈ [3,35], 정수
- |Δ_t − Δ_{t-1}| ≤ 15 권장
- 내부에서 waterLevel_cm ≥ 120 & 탈출 실패 → fatal 가능
- open_door → 항상 위험, feedback.bad에 반드시 경고
- 수위 ≥ 60 & delay/call_help만 → 위험↑
- open_window는 주로 waterLevel ≤ 50에서만 성공, 그 이상은 break_window 필요
- 외부+flow="fast" & swim_in_current → injured/fatal 위험↑

# 출력 JSON 스키마
{
  "action_intent": ["string", ...],
  "event": null | {
    "desc": "string",
    "source_refs": ["docId#chunk-12"]   // 자료를 쓴 경우 반드시 출처 표시, 없으면 []
  },
  "effects": [
    { "type": "injury|delta_adjust|flow_change|position_change|...",
      "value": "any",
      "reason": "string" }
  ],
  "delta_water_cm": 12,
  "state_update": {
    "turn": 2,
    "waterLevel_cm": 20,
    "health_state": "normal|injured|fatal",
    "position": "inside|outside|on-roof",
    "flow": "slow|fast"
  },
  "outcome": "normal|injured|fatal|null",
  "feedback": {
    "good": ["..."],
    "bad": ["..."],
    "next_best": ["..."]
  },
  "lesson": "string",   // 매 상황에 따른 교훈 즉석 생성, 필요시 출처 반영
  "inner_monologue": "string"
}
