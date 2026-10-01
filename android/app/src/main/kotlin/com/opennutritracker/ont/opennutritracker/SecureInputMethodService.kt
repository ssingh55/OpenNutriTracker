package com.opennutritracker.ont.opennutritracker

import android.inputmethodservice.InputMethodService
import android.view.View
import android.widget.Button
import android.view.KeyEvent

class SecureInputMethodService : InputMethodService() {
    override fun onCreateInputView(): View {
        // Implement secure keyboard UI here.
        // This example assumes a layout file named 'secure_keyboard.xml'
        // and logic to handle key presses and commit text via commitText().
        val view = layoutInflater.inflate(R.layout.secure_keyboard, null)
        
        // Wire up key buttons to commitText() — no external library, no logging
        wireupKeyButtons(view)
        
        return view
    }

    override fun onInitializeInterface() {
        super.onInitializeInterface()
        // Initialize the input method interface if needed
    }

    override fun onStartInput(attribute: android.view.inputmethod.EditorInfo?, restarting: Boolean) {
        super.onStartInput(attribute, restarting)
        // Handle start of input
    }

    private fun wireupKeyButtons(view: View) {
        // Find all buttons in the secure keyboard layout and attach click listeners
        // that commit their text via commitText()
        val digitButtons = listOf(
            view.findViewById<Button?>(R.id.btn_0),
            view.findViewById<Button?>(R.id.btn_1),
            view.findViewById<Button?>(R.id.btn_2),
            view.findViewById<Button?>(R.id.btn_3),
            view.findViewById<Button?>(R.id.btn_4),
            view.findViewById<Button?>(R.id.btn_5),
            view.findViewById<Button?>(R.id.btn_6),
            view.findViewById<Button?>(R.id.btn_7),
            view.findViewById<Button?>(R.id.btn_8),
            view.findViewById<Button?>(R.id.btn_9)
        )

        digitButtons.forEach { button ->
            button?.setOnClickListener { clickedButton ->
                val text = (clickedButton as Button).text.toString()
                currentInputConnection?.commitText(text, 1)
            }
        }

        // Handle backspace
        view.findViewById<Button?>(R.id.btn_backspace)?.setOnClickListener {
            currentInputConnection?.sendKeyEvent(
                KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL)
            )
            currentInputConnection?.sendKeyEvent(
                KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL)
            )
        }

        // Handle clear/reset
        view.findViewById<Button?>(R.id.btn_clear)?.setOnClickListener {
            currentInputConnection?.deleteSurroundingText(Int.MAX_VALUE, Int.MAX_VALUE)
        }
    }
}
