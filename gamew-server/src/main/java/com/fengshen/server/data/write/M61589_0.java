package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M61589_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_61589_0 object2 = (Vo_61589_0)object;
        GameWriteTool.writeString(writeBuf, object2.key0);
        GameWriteTool.writeShort(writeBuf, object2.settingkey0);
        GameWriteTool.writeString(writeBuf, object2.key1);
        GameWriteTool.writeShort(writeBuf, object2.settingkey1);
        GameWriteTool.writeString(writeBuf, object2.key2);
        GameWriteTool.writeShort(writeBuf, object2.settingkey2);
        GameWriteTool.writeString(writeBuf, object2.key3);
        GameWriteTool.writeShort(writeBuf, object2.settingkey3);
        GameWriteTool.writeString(writeBuf, object2.key4);
        GameWriteTool.writeShort(writeBuf, object2.settingkey4);
        GameWriteTool.writeString(writeBuf, object2.key5);
        GameWriteTool.writeShort(writeBuf, object2.settingkey5);
        GameWriteTool.writeString(writeBuf, object2.key6);
        GameWriteTool.writeShort(writeBuf, object2.settingkey6);
        GameWriteTool.writeString(writeBuf, object2.key7);
        GameWriteTool.writeShort(writeBuf, object2.settingkey7);
        GameWriteTool.writeString(writeBuf, object2.key8);
        GameWriteTool.writeShort(writeBuf, object2.settingkey8);
        GameWriteTool.writeString(writeBuf, object2.key9);
        GameWriteTool.writeShort(writeBuf, object2.settingkey9);
        GameWriteTool.writeString(writeBuf, object2.key10);
        GameWriteTool.writeShort(writeBuf, object2.settingkey10);
        GameWriteTool.writeString(writeBuf, object2.key11);
        GameWriteTool.writeShort(writeBuf, object2.settingkey11);
        GameWriteTool.writeString(writeBuf, object2.key12);
        GameWriteTool.writeShort(writeBuf, object2.settingkey12);
        GameWriteTool.writeString(writeBuf, object2.key13);
        GameWriteTool.writeShort(writeBuf, object2.settingkey13);
        GameWriteTool.writeString(writeBuf, object2.key14);
        GameWriteTool.writeShort(writeBuf, object2.settingkey14);
        GameWriteTool.writeString(writeBuf, object2.key15);
        GameWriteTool.writeShort(writeBuf, object2.settingkey15);
        GameWriteTool.writeString(writeBuf, object2.key16);
        GameWriteTool.writeShort(writeBuf, object2.settingkey16);
        GameWriteTool.writeString(writeBuf, object2.key17);
        GameWriteTool.writeShort(writeBuf, object2.settingkey17);
        GameWriteTool.writeString(writeBuf, object2.key18);
        GameWriteTool.writeShort(writeBuf, object2.settingkey18);
        GameWriteTool.writeString(writeBuf, object2.key19);
        GameWriteTool.writeShort(writeBuf, object2.settingkey19);
        GameWriteTool.writeString(writeBuf, object2.key20);
        GameWriteTool.writeShort(writeBuf, object2.settingkey20);
        GameWriteTool.writeString(writeBuf, object2.key21);
        GameWriteTool.writeShort(writeBuf, object2.settingkey21);
        GameWriteTool.writeString(writeBuf, object2.key22);
        GameWriteTool.writeShort(writeBuf, object2.settingkey22);
        GameWriteTool.writeString(writeBuf, object2.key23);
        GameWriteTool.writeShort(writeBuf, object2.settingkey23);
        GameWriteTool.writeString(writeBuf, object2.key24);
        GameWriteTool.writeShort(writeBuf, object2.settingkey24);
        GameWriteTool.writeString(writeBuf, object2.key25);
        GameWriteTool.writeShort(writeBuf, object2.settingkey25);
        GameWriteTool.writeString(writeBuf, object2.key26);
        GameWriteTool.writeShort(writeBuf, object2.settingkey26);
        GameWriteTool.writeString(writeBuf, object2.key27);
        GameWriteTool.writeShort(writeBuf, object2.settingkey27);
        GameWriteTool.writeString(writeBuf, object2.key28);
        GameWriteTool.writeShort(writeBuf, object2.settingkey28);
        GameWriteTool.writeString(writeBuf, object2.key29);
        GameWriteTool.writeShort(writeBuf, object2.settingkey29);
        GameWriteTool.writeString(writeBuf, object2.key30);
        GameWriteTool.writeShort(writeBuf, object2.settingkey30);
        GameWriteTool.writeString(writeBuf, object2.key31);
        GameWriteTool.writeShort(writeBuf, object2.settingkey31);
        GameWriteTool.writeString(writeBuf, object2.key32);
        GameWriteTool.writeShort(writeBuf, object2.settingkey32);
        GameWriteTool.writeString(writeBuf, object2.key33);
        GameWriteTool.writeShort(writeBuf, object2.settingkey33);
        GameWriteTool.writeString(writeBuf, object2.key34);
        GameWriteTool.writeShort(writeBuf, object2.settingkey34);
        GameWriteTool.writeString(writeBuf, object2.key35);
        GameWriteTool.writeShort(writeBuf, object2.settingkey35);
        GameWriteTool.writeString(writeBuf, object2.key36);
        GameWriteTool.writeShort(writeBuf, object2.settingkey36);
        GameWriteTool.writeString(writeBuf, object2.key37);
        GameWriteTool.writeShort(writeBuf, object2.settingkey37);
        GameWriteTool.writeString(writeBuf, object2.key38);
        GameWriteTool.writeShort(writeBuf, object2.settingkey38);
        GameWriteTool.writeString(writeBuf, object2.key39);
        GameWriteTool.writeShort(writeBuf, object2.settingkey39);
        GameWriteTool.writeString(writeBuf, object2.key40);
        GameWriteTool.writeShort(writeBuf, object2.settingkey40);
        GameWriteTool.writeString(writeBuf, object2.key41);
        GameWriteTool.writeShort(writeBuf, object2.settingkey41);
        GameWriteTool.writeString(writeBuf, object2.key42);
        GameWriteTool.writeShort(writeBuf, object2.settingkey42);
        GameWriteTool.writeString(writeBuf, object2.key43);
        GameWriteTool.writeShort(writeBuf, object2.settingkey43);
        GameWriteTool.writeString(writeBuf, object2.key44);
        GameWriteTool.writeShort(writeBuf, object2.settingkey44);
        GameWriteTool.writeString(writeBuf, object2.key45);
        GameWriteTool.writeShort(writeBuf, object2.settingkey45);
        GameWriteTool.writeString(writeBuf, object2.key46);
        GameWriteTool.writeShort(writeBuf, object2.settingkey46);
        GameWriteTool.writeString(writeBuf, object2.key47);
        GameWriteTool.writeShort(writeBuf, object2.settingkey47);
        GameWriteTool.writeString(writeBuf, object2.key48);
        GameWriteTool.writeShort(writeBuf, object2.settingkey48);
        GameWriteTool.writeString(writeBuf, object2.key49);
        GameWriteTool.writeShort(writeBuf, object2.settingkey49);
        GameWriteTool.writeString(writeBuf, object2.key50);
        GameWriteTool.writeShort(writeBuf, object2.settingkey50);
        GameWriteTool.writeString(writeBuf, object2.key51);
        GameWriteTool.writeShort(writeBuf, object2.settingkey51);
        GameWriteTool.writeString(writeBuf, object2.key52);
        GameWriteTool.writeShort(writeBuf, object2.settingkey52);
        GameWriteTool.writeString(writeBuf, object2.key53);
        GameWriteTool.writeShort(writeBuf, object2.settingkey53);
        GameWriteTool.writeString(writeBuf, object2.key54);
        GameWriteTool.writeShort(writeBuf, object2.settingkey54);
        GameWriteTool.writeString(writeBuf, object2.key55);
        GameWriteTool.writeShort(writeBuf, object2.settingkey55);
        GameWriteTool.writeString(writeBuf, object2.key56);
        GameWriteTool.writeShort(writeBuf, object2.settingkey56);
        GameWriteTool.writeString(writeBuf, object2.key57);
        GameWriteTool.writeShort(writeBuf, object2.settingkey57);
        GameWriteTool.writeString(writeBuf, object2.key58);
        GameWriteTool.writeShort(writeBuf, object2.settingkey58);
        GameWriteTool.writeString(writeBuf, object2.key59);
        GameWriteTool.writeShort(writeBuf, object2.settingkey59);
        GameWriteTool.writeString(writeBuf, object2.key60);
        GameWriteTool.writeShort(writeBuf, object2.settingkey60);
    }
    
    @Override
    public int cmd() {
        return 61589;
    }
}
