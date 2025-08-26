현재 상태:
- turn: {{turn}}
- waterLevel_cm: {{waterLevel_cm}}
- health_state: {{health_state}}
- position: {{position}}
- flow: {{flow}}

사용자 입력:
"{{user_text}}"

요청:
- 위 상태와 사용자 입력을 해석하여 action_intent를 도출하라.
- event_candidates(아래)가 있어도, 이번 턴에 의미가 없다면 이벤트를 생성하지 말라(event=null).
- 이벤트 유무와 관계없이 delta_water_cm(3~35)과 state_update는 네가 직접 결정하라.
- 이벤트 발생 시, 그 효과(부상, 수위 가속, 흐름/위치 변화 등)를 네가 직접 반영하여 delta_water_cm과 state_update를 갱신하라.
- 내부에서 waterLevel_cm ≥ 120인데 탈출 실패로 판단되면 fatal을 확정할 수 있다.
- 반드시 JSON 스키마를 지켜라. 불필요한 텍스트 출력 금지.

event_candidates:
{{retrieved_chunks}}   # 없으면 "(없음)"
